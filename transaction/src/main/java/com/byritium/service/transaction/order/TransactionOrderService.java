package com.byritium.service.transaction.order;

import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.service.payment.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Service
@Slf4j
public class TransactionOrderService {


    @Resource
    private PayService payService;


    @Resource
    private TransactionOrderService transactionOrderService;

    @Resource
    private TransactionTemplate transactionTemplate;

    public TransactionResult trade(String clientId, TransactionParam param) {
//        PaymentSetting paymentChannel = param.getPaymentPattern();
//        Map<PaymentSetting, TransactionPayOrder> map = new HashMap<>();
//        String userId = param.getUserId();
//
//        BigDecimal reductionAmount = BigDecimal.ZERO;
//        BigDecimal reductionAmountQuota = param.getOrderAmount().multiply(TransactionConst.TX_AMOUNT_MAX_REDUCTION_COEFFICIENT);
//
//        String couponId = param.getCouponId();
//        if (StringUtils.hasText(couponId) && reductionAmountQuota.compareTo(BigDecimal.ZERO) > 0) {
//            TransactionPayOrder transactionPayOrder = payOrderService.buildCouponOrder(couponId);
//            map.put(PaymentSetting.COUPON_PAY, transactionPayOrder);
//            reductionAmount = reductionAmount.add(transactionPayOrder.getOrderAmount());
//        }
//
//        Deduction deduction = param.getDeduction();
//        if (deduction != null && reductionAmountQuota.compareTo(BigDecimal.ZERO) > 0) {
//            TransactionPayOrder transactionPayOrder = payOrderService.buildDeductionOrder(userId, deduction, reductionAmountQuota);
//            map.put(deduction.getPaymentPattern(), transactionPayOrder);
//            reductionAmount = reductionAmount.add(transactionPayOrder.getOrderAmount());
//        }
//
//        if (paymentChannel != null) {
//            BigDecimal corePaymentOrderAmount = param.getOrderAmount().subtract(reductionAmount);
//            corePaymentOrderAmount = corePaymentOrderAmount.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : corePaymentOrderAmount;
//
//            map.put(paymentChannel, payOrderService.buildCoreOrder(paymentChannel, userId, corePaymentOrderAmount));
//        }
//
//        TransactionOrder transactionTradeOrder = new TransactionOrder(clientId, param);
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                transactionOrderService.save(transactionTradeOrder);
//                String transactionOrderId = transactionTradeOrder.getId();
//
//                for (Map.Entry<PaymentSetting, TransactionPayOrder> entry : map.entrySet()) {
//                    TransactionPayOrder transactionPayOrder = entry.getValue();
//                    transactionPayOrder.setTransactionOrderId(transactionOrderId);
//                    payOrderService.save(transactionPayOrder);
//                }
//            }
//        });
//
//        List<CompletableFuture<TransactionPayOrder>> futureList = map.values().stream().map(
//                        (TransactionPayOrder order) -> CompletableFuture.supplyAsync(() -> {
//                            PaymentRequest paymentRequest = new PaymentRequest();
//                            PaymentResult paymentResult = paymentService.call(paymentRequest);
//                            order.setState(paymentResult.getState());
//                            order.setSign(paymentResult.getSign());
//                            return order;
//                        }))
//                .collect(Collectors.toList());
//
//        return payOrderService.executePayment(transactionTradeOrder, futureList);

        return null;

    }
}
