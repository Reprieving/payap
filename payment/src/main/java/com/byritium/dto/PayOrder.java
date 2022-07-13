package com.byritium.dto;


import com.byritium.constance.PaymentChannel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayOrder {
    private String id;
    private String userId;
    private String subject;
    private String trxOrderId;
    private String bizOrderId;
    private BigDecimal orderAmount;
    private PaymentChannel paymentChannel;
}
