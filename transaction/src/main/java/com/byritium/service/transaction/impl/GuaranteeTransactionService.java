package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dto.Deduction;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.transaction.TransactionPayOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.ITransactionCallBackService;
import com.byritium.service.transaction.ITransactionCallService;
import com.byritium.service.transaction.order.PayOrderService;
import com.byritium.service.transaction.order.TransactionOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GuaranteeTransactionService implements ITransactionCallService, ITransactionCallBackService {
    public GuaranteeTransactionService(TransactionOrderService transactionOrderService, PayOrderService payOrderService, PaymentService paymentService) {
        this.transactionOrderService = transactionOrderService;
        this.payOrderService = payOrderService;
        this.paymentService = paymentService;
    }

    @Override
    public TransactionType type() {
        return TransactionType.GUARANTEE;
    }

    private final TransactionOrderService transactionOrderService;
    private final PayOrderService payOrderService;
    private final PaymentService paymentService;

    public TransactionResult call(String clientId, TransactionParam param) {
        return null;
    }


    public TransactionResult call(TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();
        PaymentChannel paymentChannel = param.getPaymentChannel();
        Map<PaymentChannel, TransactionPayOrder> map = new HashMap<>();
        String userId = param.getUserId();

        BigDecimal orderAmount = param.getOrderAmount();

        String couponId = param.getCouponId();
        if (StringUtils.hasText(couponId)) {
            TransactionPayOrder transactionPayOrder = payOrderService.buildCouponOrder(couponId);
            map.put(PaymentChannel.COUPON_PAY, transactionPayOrder);
        }

        Deduction deduction = param.getDeduction();
        if (deduction != null) {
            TransactionPayOrder transactionPayOrder = payOrderService.buildDeductionOrder(userId, deduction);
            map.put(deduction.getPaymentChannel(), transactionPayOrder);
        }

        if (paymentChannel != null) {
            map.put(paymentChannel, payOrderService.buildCoreOrder(paymentChannel, userId, orderAmount));
        }

        TransactionTradeOrder transactionTradeOrder = new TransactionTradeOrder(param);
        transactionOrderService.save(transactionTradeOrder);
        String transactionOrderId = transactionTradeOrder.getId();

        for (Map.Entry<PaymentChannel, TransactionPayOrder> entry : map.entrySet()) {
            TransactionPayOrder transactionPayOrder = entry.getValue();
            transactionPayOrder.setTransactionOrderId(transactionOrderId);
            payOrderService.save(transactionPayOrder);
        }

        PaymentResult paymentResult = paymentService.pay(map.get(paymentChannel));
        transactionResult.setPaySign(paymentResult.getSign());
        return transactionResult;
    }

    @Override
    public void paymentCallback(PaymentResult paymentResult) {
        TransactionPayOrder transactionPayOrder = payOrderService.get(paymentResult.getPaymentOrderId());
        if (transactionPayOrder == null) {
            throw new BusinessException("未找到订单");
        }

        String transactionId = transactionPayOrder.getTransactionOrderId();
        TransactionTradeOrder transactionTradeOrder = transactionOrderService.get(transactionId);
        PaymentState paymentState = paymentResult.getState();
        transactionPayOrder.setState(paymentState);
        payOrderService.update(transactionPayOrder);

        if (PaymentState.PAYMENT_SUCCESS == paymentState) {
            List<TransactionPayOrder> transactionPayOrderList = payOrderService.listByNotCoreFlag(transactionId);
            List<CompletableFuture<TransactionPayOrder>> futureList = transactionPayOrderList.stream().map(
                            (TransactionPayOrder order) -> CompletableFuture.supplyAsync(() -> {
                                PaymentResult result = paymentService.pay(order);
                                order.setState(result.getState());
                                return order;
                            }))
                    .collect(Collectors.toList());

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
            CompletableFuture<List<TransactionPayOrder>> futureResult = allFutures.thenApply(v -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
            List<TransactionPayOrder> transactionPayOrders;
            try {
                transactionPayOrders = futureResult.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("get payment order exception", e);
                throw new BusinessException("get payment order exception");
            }

            if (verifyAllSuccess(transactionPayOrders)) {
                payOrderService.saveAll(transactionPayOrders);
                transactionTradeOrder.setPaymentState(PaymentState.PAYMENT_SUCCESS);
            } else {
                transactionTradeOrder.setPaymentState(PaymentState.PAYMENT_FAIL);
            }

            transactionOrderService.save(transactionTradeOrder);

        }

    }

    @Override
    public void refundCallback(PaymentResult paymentResult) {

    }
}
