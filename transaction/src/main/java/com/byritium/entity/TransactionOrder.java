package com.byritium.entity;

import com.byritium.constance.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class TransactionOrder {
    private String id;
    private String clientId;
    private String businessOrderId;
    private String payerId;
    private String payeeId;
    private String subject;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private PaymentChannel paymentChannel;
    private TransactionType transactionType;
    private TransactionState transactionState;
    private Os os;
    private Timestamp createTime;
}
