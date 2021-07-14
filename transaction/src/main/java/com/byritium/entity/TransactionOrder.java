package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TransactionOrder {
    private String id;
    private String userId;
    private String businessOrderId;
    private BigDecimal amount;
    private PaymentChannel paymentChannel;
    private TransactionType transactionType;
    private Timestamp createTime;
}
