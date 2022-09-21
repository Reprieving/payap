package com.byritium.entity.payment;

import com.byritium.constance.PaymentPattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentOrder {
    private String id;
    private String userId;
    private String subject;
    private String trxOrderId;
    private String bizOrderId;
    private BigDecimal orderAmount;
    private PaymentPattern paymentPattern;
}
