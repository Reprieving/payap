package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.PaymentState;
import lombok.Data;

@Data
public class PaymentResultItem {
    private PaymentPattern paymentPattern;
    private String paymentOrderId;
    private PaymentState state;
    private String sign;
}
