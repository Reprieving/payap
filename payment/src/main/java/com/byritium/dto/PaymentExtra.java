package com.byritium.dto;

import com.byritium.constance.AssetsType;
import com.byritium.constance.PaymentPattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentExtra {
    private PaymentPattern paymentPattern;
    private AssetsType assetsType;
    private String autoCode;
}
