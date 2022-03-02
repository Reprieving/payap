package com.byritium.entity;

import com.byritium.constance.*;
import com.byritium.dto.TransactionParam;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionReceiptOrder extends CommonEntity {
    private String id;
    private String clientId;
    private String businessOrderId;
    private String userId;
    private String payerId;
    private String sellerId;
    private String title;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private TransactionType transactionType;
    private TransactionState transactionState;
    private PaymentChannel paymentChannel;
    private PaymentState paymentState;

    public TransactionReceiptOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
        this.paymentState = PaymentState.PAYMENT_PENDING;
    }

    public TransactionReceiptOrder(String clientId, TransactionParam param) {
        TransactionReceiptOrder transactionReceiptOrder = new TransactionReceiptOrder();
        transactionReceiptOrder.setClientId(clientId);
        transactionReceiptOrder.setBusinessOrderId(param.getBusinessOrderId());
        transactionReceiptOrder.setTitle(param.getTitle());
        transactionReceiptOrder.setUserId(param.getUserId());
        transactionReceiptOrder.setSellerId(param.getSellerId());
        transactionReceiptOrder.setPaymentChannel(paymentChannel);
        transactionReceiptOrder.setOrderAmount(param.getOrderAmount());
        transactionReceiptOrder.setTransactionType(param.getTransactionType());
        transactionReceiptOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionReceiptOrder.setCreateTime(LocalDateTime.now());
    }
}
