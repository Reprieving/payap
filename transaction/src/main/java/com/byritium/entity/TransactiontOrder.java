package com.byritium.entity;

import com.byritium.constance.*;
import com.byritium.dto.TransactionParam;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactiontOrder extends CommonEntity {
    private String id;
    private String clientId;
    private String businessOrderId;
    private String userId;
    private String payerId;
    private String payeeId;
    private String title;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private TransactionType transactionType;
    private TransactionState transactionState;
    private PaymentChannel paymentChannel;
    private PaymentState paymentState;

    public TransactiontOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
        this.paymentState = PaymentState.PAYMENT_PENDING;
    }

    public TransactiontOrder(String clientId, TransactionParam param) {
        TransactiontOrder transactiontOrder = new TransactiontOrder();
        transactiontOrder.setClientId(clientId);
        transactiontOrder.setBusinessOrderId(param.getBusinessOrderId());
        transactiontOrder.setTitle(param.getTitle());
        transactiontOrder.setUserId(param.getUserId());
        transactiontOrder.setPayeeId(param.getPayeeId());
        transactiontOrder.setPaymentChannel(param.getPaymentChannel());
        transactiontOrder.setOrderAmount(param.getOrderAmount());
        transactiontOrder.setTransactionType(param.getTransactionType());
        transactiontOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactiontOrder.setCreateTime(LocalDateTime.now());
    }
}
