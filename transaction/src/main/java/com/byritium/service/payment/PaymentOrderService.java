package com.byritium.service.payment;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.PaymentType;
import com.byritium.dao.PayOrderDao;
import com.byritium.dto.*;
import com.byritium.entity.PayOrder;
import com.byritium.entity.TransactionOrder;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.CouponRpc;
import com.byritium.service.transaction.TransactionOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentOrderService implements ApplicationContextAware {
    private static Map<PaymentType, IPaymentService> paymentServiceMap;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private CouponRpc couponRpc;

    @Resource
    private TransactionOrderService transactionOrderService;

    @Resource
    private PayOrderDao payOrderDao;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        paymentServiceMap = new HashMap<>();
        Map<String, IPaymentService> map = applicationContext.getBeansOfType(IPaymentService.class);
        map.forEach((key, value) -> {
            if (value.type() != null)
                paymentServiceMap.put(value.type(), value);
        });
    }

    public PaymentResult callPayment(PaymentType paymentType, PayOrder payOrder) {
        return paymentServiceMap.get(paymentType).call(payOrder);
    }

    public PayOrder save(String transactionOrderId, PaymentChannel paymentChannel, BigDecimal amount, String payerId, String mediumId) {
        PayOrder payOrder = new PayOrder();
        payOrder.setTransactionOrderId(transactionOrderId);
        payOrder.setPaymentChannel(paymentChannel);
        payOrder.setPayerId(null);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        if (StringUtils.hasText(mediumId)) {
            payOrder.setPayMediumId(mediumId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);

        payOrderDao.save(payOrder);

        return payOrder;
    }

    public PayOrder buildCoreOrder(PaymentChannel paymentChannel, String payerId, BigDecimal amount) {
        PayOrder payOrder = new PayOrder();
        payOrder.setPaymentChannel(paymentChannel);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);

        payOrderDao.save(payOrder);
        return payOrder;
    }

    public PayOrder buildCoreOrder(String transactionOrderId, PaymentChannel paymentChannel, String payerId, BigDecimal amount) {
        PayOrder payOrder = new PayOrder();
        payOrder.setTransactionOrderId(transactionOrderId);
        payOrder.setPaymentChannel(paymentChannel);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setPaymentAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);

        return payOrder;
    }

    public PayOrder buildCouponOrder(String couponId) {
        PaymentChannel paymentChannel = PaymentChannel.COUPON_PAY;
        ResponseBody<CouponInfo> responseBody = couponRpc.get(couponId);
        CouponInfo couponInfo = responseBody.getData();
        String payerId = couponInfo.getPayerId();
        BigDecimal amount = couponInfo.getAmount();
        PayOrder payOrder = new PayOrder();
        payOrder.setPaymentChannel(paymentChannel);
        payOrder.setPayerId(couponInfo.getPayerId());
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        if (StringUtils.hasText(couponId)) {
            payOrder.setPayMediumId(couponId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setPaymentAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);


        return payOrder;
    }

    public PayOrder buildDeductionOrder(String payerId, Deduction deduction, BigDecimal reductionAmountQuota) {
        PaymentChannel paymentChannel = deduction.getPaymentChannel();
        PayOrder payOrder = new PayOrder();
        payOrder.setPaymentChannel(paymentChannel);
        payOrder.setPayerId(null);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());

        AccountQuery accountQuery = new AccountQuery();
        ResponseBody<AccountBalance> responseBody = accountRpc.query(accountQuery);

        AccountBalance accountBalance = responseBody.getData();
        BigDecimal balanceAmount = accountBalance.getAmount();
        BigDecimal balanceValue = accountBalance.getValue();
        BigDecimal rate = balanceValue.divide(balanceAmount, MathContext.DECIMAL32);

        if (reductionAmountQuota.compareTo(balanceValue) >= 0) {
            payOrder.setOrderAmount(balanceAmount);
            payOrder.setPaymentAmount(balanceValue);
        } else {
            payOrder.setOrderAmount(reductionAmountQuota);
            payOrder.setPaymentAmount(reductionAmountQuota.multiply(rate));
        }

        payOrder.setState(PaymentState.PAYMENT_WAITING);


        return payOrder;
    }

    public PayOrder save(PayOrder payOrder) {
        return payOrderDao.save(payOrder);
    }

    public PayOrder getByTxOrderIdAndPaymentChannel(String orderId, PaymentChannel paymentChannel) {
        return payOrderDao.findByTransactionOrderIdAndPaymentChannel(orderId, paymentChannel);
    }

    public List<PayOrder> listByTxOrderId(String orderId) {
        return payOrderDao.findByTransactionOrderId(orderId);
    }

    public Iterable<PayOrder> saveAll(Iterable<PayOrder> iterable) {
        return payOrderDao.saveAll(iterable);
    }

    public boolean verifyAllSuccess(List<PayOrder> list) {
        return list.stream().filter(transactionPayOrder -> transactionPayOrder.getState() == PaymentState.PAYMENT_SUCCESS).count() == list.size();
    }


    public TransactionResult executePayment(TransactionOrder transactionOrder, List<CompletableFuture<PayOrder>> futureList) {
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        CompletableFuture<List<PayOrder>> futureResult = allFutures.thenApply(v -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        PaymentChannel paymentChannel = transactionOrder.getPaymentChannel();
        return transactionTemplate.execute(transactionStatus -> {
            TransactionResult transactionResult = new TransactionResult();
            List<PayOrder> payOrders;
            try {
                payOrders = futureResult.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("get payment order exception", e);
                throw new BusinessException("get payment order exception");
            }
            payOrderDao.saveAll(payOrders);
            transactionResult.setPaymentOrders(payOrders.stream().collect(Collectors.toMap(PayOrder::getPaymentChannel, TransactionPayOrder -> TransactionPayOrder)));

            if (paymentChannel != null && verifyAllSuccess(payOrders)) {
                transactionResult.setPaymentState(PaymentState.PAYMENT_SUCCESS);
                transactionOrder.setPaymentState(PaymentState.PAYMENT_SUCCESS);
                transactionOrderService.save(transactionOrder);
            }
            return transactionResult;
        });
    }


}