package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.dao.TransactionPayOrderRepository;
import com.byritium.dto.Deduction;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.TransactionPayOrder;
import com.byritium.rpc.PaymentPayRpc;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TransactionPayOrderService {

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Resource
    private TransactionPayOrderRepository transactionPayOrderRepository;

    public TransactionPayOrder saveOrder(String transactionOrderId, PaymentChannel paymentChannel, BigDecimal amount, String payerId, String mediumId) {
        TransactionPayOrder payOrder = new TransactionPayOrder();
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

        transactionPayOrderRepository.save(payOrder);

        return payOrder;
    }

    public TransactionPayOrder saveCoreOrder(String transactionOrderId, PaymentChannel paymentChannel, String payerId, BigDecimal amount) {
        return null;
    }

    public TransactionPayOrder saveCouponOrder(String transactionOrderId, String couponId) {
        PaymentChannel paymentChannel = PaymentChannel.COUPON_PAY;
        return null;
    }

    public TransactionPayOrder saveDeductionOrder(String transactionOrderId, String payerId, Deduction deduction) {
        return null;
    }

    public TransactionPayOrder saveOrder(TransactionPayOrder transactionPayOrder) {
        return transactionPayOrderRepository.save(transactionPayOrder);
    }

    public CompletableFuture<TransactionPayOrder> payOrder(TransactionPayOrder transactionPayOrder) {

        return CompletableFuture.supplyAsync(() -> {
            ResponseBody<PaymentResult> response = paymentPayRpc.pay(transactionPayOrder);
            if (response.success()) {
                PaymentResult result = response.getData();
                transactionPayOrder.setState(result.getState());
            } else {
                //TODO Log
            }
            return transactionPayOrder;
        });
    }

    public boolean verifyAllSuccess(List<TransactionPayOrder> list) {
        return list.stream().filter(transactionPayOrder -> transactionPayOrder.getState() == PaymentState.PAYMENT_SUCCESS).count() == list.size();
    }
}