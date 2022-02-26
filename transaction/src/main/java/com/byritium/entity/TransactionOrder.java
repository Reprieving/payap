package com.byritium.entity;

import com.byritium.constance.*;
import com.byritium.dto.TransactionParam;
import com.byritium.exception.BusinessException;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class TransactionOrder extends CommonEntity {
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
        transactionOrder.setSellerId(param.getSellerId());
        transactionOrder.setPaymentChannel(paymentChannel);
        transactionOrder.setOrderAmount(param.getOrderAmount());
        transactionOrder.setTransactionType(param.getTransactionType());
        transactionOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionOrder.setCrateTime(LocalDateTime.now());
    }
}
