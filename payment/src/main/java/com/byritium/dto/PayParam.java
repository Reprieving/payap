package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayParam {
    private String sign;
    private BigDecimal amount;
    private PaymentChannel paymentChannel;
    private PaymentState paymentState;
}
