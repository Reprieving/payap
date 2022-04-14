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
public class TransactionTradeOrder extends CommonEntity {
    private String id;
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
    private PaymentChannel paymentChannel;
    private PaymentState paymentState;

    public TransactionTradeOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
        this.paymentState = PaymentState.PAYMENT_PENDING;
    }

    public TransactionTradeOrder(String clientId, TransactionParam param) {
        TransactionTradeOrder transactionTradeOrder = new TransactionTradeOrder();
        transactionTradeOrder.setClientId(clientId);
        transactionTradeOrder.setBizOrderId(param.getBusinessOrderId());
        transactionTradeOrder.setTitle(param.getTitle());
        transactionTradeOrder.setUserId(param.getUserId());
        transactionTradeOrder.setPayeeId(param.getPayeeId());
        transactionTradeOrder.setPaymentChannel(param.getPaymentChannel());
        transactionTradeOrder.setOrderAmount(param.getOrderAmount());
        transactionTradeOrder.setTransactionType(param.getTransactionType());
        transactionTradeOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionTradeOrder.setCreateTime(LocalDateTime.now());
    }
}
