package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResult {
    private String prePayId;
    private String sign;
    private PaymentPattern paymentPattern;
    private PaymentStatus paymentStatus;
}
