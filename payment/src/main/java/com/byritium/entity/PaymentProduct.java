package com.byritium.entity;

import com.byritium.constance.PaymentProductCode;
import com.byritium.constance.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class PaymentProduct {
    private String id;
    private TransactionType transactionType;
    private PaymentProductCode paymentProductCode;
    private Timestamp timestamp;
}
