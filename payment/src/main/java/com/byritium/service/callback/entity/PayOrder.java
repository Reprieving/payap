package com.byritium.service.callback.entity;


import com.byritium.constance.PaymentPattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayOrder {
    private String id;
    private String uid;
    private String subject;
    private String txOrderId;
    private String bizOrderId;
    private BigDecimal orderAmount;
    private PaymentPattern paymentPattern;
}
