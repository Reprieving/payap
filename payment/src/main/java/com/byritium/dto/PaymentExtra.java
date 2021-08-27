package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentExtra {
    private PaymentProduct paymentProduct;
    private PaymentChannel paymentChannel;
}
