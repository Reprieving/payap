package com.byritium.service.transaction.order;

import com.byritium.dao.TradeOrderDao;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.service.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Service
@Slf4j
public class TransactionOrderService {

    @Resource
    private TradeOrderDao tradeOrderDao;

    public TransactionTradeOrder save(TransactionTradeOrder transactionTradeOrder) {
        return tradeOrderDao.save(transactionTradeOrder);
    }

    public TransactionTradeOrder findByBizOrderId(String orderId) {
        return tradeOrderDao.findByBizOrderId(orderId);
    }

    @Resource
    private PayOrderService payOrderService;


    @Resource
    private PaymentService paymentService;


    @Resource
    private TransactionOrderService transactionOrderService;

    @Resource
    private TransactionTemplate transactionTemplate;

    public TransactionTradeOrder get(String id) {
        return tradeOrderDao.findById(id).orElse(null);
    }

    public TransactionResult trade(String clientId, TransactionParam param) {
//        PaymentChannel paymentChannel = param.getPaymentChannel();
//        Map<PaymentChannel, TransactionPayOrder> map = new HashMap<>();
//        String userId = param.getUserId();
//
//        BigDecimal reductionAmount = BigDecimal.ZERO;
//        BigDecimal reductionAmountQuota = param.getOrderAmount().multiply(TransactionConst.TX_AMOUNT_MAX_REDUCTION_COEFFICIENT);
//
//        String couponId = param.getCouponId();
//        if (StringUtils.hasText(couponId) && reductionAmountQuota.compareTo(BigDecimal.ZERO) > 0) {
//            TransactionPayOrder transactionPayOrder = payOrderService.buildCouponOrder(couponId);
//            map.put(PaymentChannel.COUPON_PAY, transactionPayOrder);
//            reductionAmount = reductionAmount.add(transactionPayOrder.getOrderAmount());
//        }
//
//        Deduction deduction = param.getDeduction();
//        if (deduction != null && reductionAmountQuota.compareTo(BigDecimal.ZERO) > 0) {
//            TransactionPayOrder transactionPayOrder = payOrderService.buildDeductionOrder(userId, deduction, reductionAmountQuota);
//            map.put(deduction.getPaymentChannel(), transactionPayOrder);
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
//        TransactionTradeOrder transactionTradeOrder = new TransactionTradeOrder(clientId, param);
//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                transactionOrderService.save(transactionTradeOrder);
//                String transactionOrderId = transactionTradeOrder.getId();
//
//                for (Map.Entry<PaymentChannel, TransactionPayOrder> entry : map.entrySet()) {
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
