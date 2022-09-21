package com.byritium.entity.payment;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRefundOrder {
    private String id;
    private String userId;
    private String subject;
    private String preOrderId;
    private String trxOrderId;
    private TransactionType transactionType;
    private String bizOrderId;
    private BigDecimal orderAmount;
    private PaymentPattern paymentPattern;
}
