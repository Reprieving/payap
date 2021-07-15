package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.Os;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TransactionOrder {
    private String id;
    private String clientId;
    private String businessOrderId;
    private String payerId;
    private String payeeId;
    private BigDecimal transactionAmount;
    private PaymentChannel paymentChannel;
    private TransactionType transactionType;
    private TransactionState transactionState;
    private Os os;
    private Timestamp createTime;
}
