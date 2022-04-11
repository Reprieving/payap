package com.byritium.entity;

import com.byritium.constance.*;
import com.byritium.dto.TransactionParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class TradeOrder extends CommonEntity {
    private String id;
    private String clientId;
    private String preTxOrderId;
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

    public TradeOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
        this.paymentState = PaymentState.PAYMENT_PENDING;
    }

    public TradeOrder(String clientId, TransactionParam param) {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setClientId(clientId);
        tradeOrder.setBizOrderId(param.getBusinessOrderId());
        tradeOrder.setTitle(param.getTitle());
        tradeOrder.setUserId(param.getUserId());
        tradeOrder.setPayeeId(param.getPayeeId());
        tradeOrder.setPaymentChannel(param.getPaymentChannel());
        tradeOrder.setOrderAmount(param.getOrderAmount());
        tradeOrder.setTransactionType(param.getTransactionType());
        tradeOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        tradeOrder.setCreateTime(LocalDateTime.now());
    }
}
