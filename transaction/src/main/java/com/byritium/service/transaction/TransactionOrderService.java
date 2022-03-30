package com.byritium.service.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionConst;
import com.byritium.dao.TransactionOrderDao;
import com.byritium.dto.Deduction;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.PaymentOrder;
import com.byritium.service.payment.PaymentOrderService;
import com.byritium.service.payment.impl.PayPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionOrderService {

    @Resource
    private TransactionOrderDao transactionOrderDao;

    public TransactionOrder save(TransactionOrder transactionOrder) {
        return transactionOrderDao.save(transactionOrder);
    }

    public TransactionOrder findByBizOrderId(String orderId) {
        return transactionOrderDao.findByBizOrderId(orderId);
    }

    @Resource
    private PaymentOrderService paymentOrderService;


    @Resource
    private PayPaymentService paymentService;


    @Resource
    private TransactionOrderService transactionOrderService;

    @Resource
    private TransactionTemplate transactionTemplate;

    public TransactionResult trade(String clientId, TransactionParam param) {
        PaymentChannel paymentChannel = param.getPaymentChannel();
        Map<PaymentChannel, PaymentOrder> map = new HashMap<>();
        String userId = param.getUserId();

        BigDecimal reductionAmount = BigDecimal.ZERO;
        BigDecimal reductionAmountQuota = param.getOrderAmount().multiply(TransactionConst.TX_AMOUNT_MAX_REDUCTION_COEFFICIENT);

        String couponId = param.getCouponId();
        if (StringUtils.hasText(couponId) && reductionAmountQuota.compareTo(BigDecimal.ZERO) > 0) {
            PaymentOrder payOrder = paymentOrderService.buildCouponOrder(couponId);
            map.put(PaymentChannel.COUPON_PAY, payOrder);
            reductionAmount = reductionAmount.add(payOrder.getPaymentAmount());
        }

        Deduction deduction = param.getDeduction();
        if (deduction != null && reductionAmountQuota.compareTo(BigDecimal.ZERO) > 0) {
            PaymentOrder payOrder = paymentOrderService.buildDeductionOrder(userId, deduction, reductionAmountQuota);
            map.put(deduction.getPaymentChannel(), payOrder);
            reductionAmount = reductionAmount.add(payOrder.getPaymentAmount());
        }

        if (paymentChannel != null) {
            BigDecimal corePaymentOrderAmount = param.getOrderAmount().subtract(reductionAmount);
            corePaymentOrderAmount = corePaymentOrderAmount.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : corePaymentOrderAmount;

            map.put(paymentChannel, paymentOrderService.buildCoreOrder(paymentChannel, userId, corePaymentOrderAmount));
        }

        TransactionOrder transactionOrder = new TransactionOrder(clientId, param);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                transactionOrderService.save(transactionOrder);
                String transactionOrderId = transactionOrder.getId();

                for (Map.Entry<PaymentChannel, PaymentOrder> entry : map.entrySet()) {
                    PaymentOrder paymentOrder = entry.getValue();
                    paymentOrder.setTransactionOrderId(transactionOrderId);
                    paymentOrderService.save(paymentOrder);
                }
            }
        });

        List<CompletableFuture<PaymentOrder>> futureList = map.values().stream().map(
                        (PaymentOrder order) -> CompletableFuture.supplyAsync(() -> {
                            PaymentResult paymentResult = paymentService.call(order);
                            order.setState(paymentResult.getState());
                            order.setSign(paymentResult.getSign());
                            return order;
                        }))
                .collect(Collectors.toList());
        return paymentOrderService.executePayment(transactionOrder, futureList);

    }
}
