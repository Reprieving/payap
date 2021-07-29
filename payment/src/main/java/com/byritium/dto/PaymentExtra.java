package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import lombok.Data;

@Data
public class PaymentExtra {
    private PaymentProduct paymentProduct;
    private PaymentChannel paymentChannel;
}
