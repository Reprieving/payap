package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import lombok.Data;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayParam {
    private String prePayId;
    private BigDecimal amount;
    private PaymentChannel paymentChannel;
    private PaymentState paymentState;
}
