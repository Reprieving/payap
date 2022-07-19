package com.byritium.entity;


import com.byritium.constance.PaymentChannel;
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
    private PaymentChannel paymentChannel;
}
