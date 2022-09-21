package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Deduction {
    private PaymentPattern paymentPattern;
    private BigDecimal amount;
}
