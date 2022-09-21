package com.byritium.entity.payment;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRechargeOrder {
    private String id;
    private String userId;
    private String subject;
    private String trxOrderId;
    private String bizOrderId;
    private TransactionType transactionType;
    private BigDecimal orderAmount;
    private PaymentPattern paymentPattern;
}
