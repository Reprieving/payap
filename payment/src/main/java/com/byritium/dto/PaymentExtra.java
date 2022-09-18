package com.byritium.dto;

import com.byritium.constance.AssetsType;
import com.byritium.constance.PaymentChannel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentExtra {
    private PaymentChannel paymentChannel;
    private AssetsType assetsType;
    private String autoCode;
}
