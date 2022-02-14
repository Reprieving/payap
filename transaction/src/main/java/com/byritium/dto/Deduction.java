package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
@Setter
public class Deduction {
    private PaymentChannel paymentChannel;
    private BigDecimal amount;
}
