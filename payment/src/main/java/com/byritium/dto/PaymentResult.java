package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentStatus;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResult {
    private String prePayId;
    private BigDecimal amount;
    private PaymentChannel paymentChannel;
    private PaymentStatus paymentStatus;
}
