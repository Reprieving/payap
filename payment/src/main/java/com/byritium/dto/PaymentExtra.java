package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import lombok.Data;

@Data
public class PaymentExtra {
    private PaymentChannel paymentChannel;
}
