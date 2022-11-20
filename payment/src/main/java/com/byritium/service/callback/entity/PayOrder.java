package com.byritium.service.callback.entity;


import com.byritium.constance.PaymentPattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayOrder {
    private Long id;
    private Long uid;
    private String subject;
    private Long txOrderId;
    private Long bizOrderId;
    private BigDecimal orderAmount;
    private PaymentPattern paymentPattern;
}
