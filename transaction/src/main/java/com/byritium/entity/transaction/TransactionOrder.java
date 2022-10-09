package com.byritium.entity.transaction;

import com.byritium.constance.*;
import com.byritium.dto.TransactionParam;
import com.byritium.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionOrder extends CommonEntity {
    private Long id;
    private String clientId;
    private String bizOrderId;
    private String userId;
    private String payerId;
    private String payeeId;
    private String title;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private TransactionType transactionType;
    private TransactionState transactionState;
    private RefundState refundState;
    private PaymentPattern paymentPattern;
    private PaymentState paymentState;

    public TransactionOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
        this.paymentState = PaymentState.PAYMENT_PENDING;
    }

    public TransactionOrder(TransactionParam param) {
        TransactionOrder transactionOrder = new TransactionOrder();
        transactionOrder.setClientId(param.getClientId());
        transactionOrder.setBizOrderId(param.getBusinessOrderId());
        transactionOrder.setTitle(param.getTitle());
        transactionOrder.setUserId(param.getUserId());
        transactionOrder.setPayeeId(param.getPayeeId());
        transactionOrder.setPaymentPattern(param.getPaymentPattern());
        transactionOrder.setOrderAmount(param.getOrderAmount());
        transactionOrder.setTransactionType(param.getTransactionType());
        transactionOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionOrder.setCreateTime(LocalDateTime.now());
    }
}
