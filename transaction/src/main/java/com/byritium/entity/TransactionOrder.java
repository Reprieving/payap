package com.byritium.entity;

import com.byritium.constance.*;
import com.byritium.dto.TransactionParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionOrder extends CommonEntity {
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

    public TransactionOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
        this.paymentState = PaymentState.PAYMENT_PENDING;
    }

    public TransactionOrder(String clientId, TransactionParam param) {
        TransactionOrder transactionOrder = new TransactionOrder();
        transactionOrder.setClientId(clientId);
        transactionOrder.setBusinessOrderId(param.getBusinessOrderId());
        transactionOrder.setTitle(param.getTitle());
        transactionOrder.setUserId(param.getUserId());
        transactionOrder.setPayeeId(param.getPayeeId());
        transactionOrder.setPaymentChannel(param.getPaymentChannel());
        transactionOrder.setOrderAmount(param.getOrderAmount());
        transactionOrder.setTransactionType(param.getTransactionType());
        transactionOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionOrder.setCreateTime(LocalDateTime.now());
    }
}
