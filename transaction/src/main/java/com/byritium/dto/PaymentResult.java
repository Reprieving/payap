package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.PaymentState;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.List;

@Data
public class PaymentResult {
    private String transactionOrderId;
    private PaymentPattern paymentPattern;
    private PaymentState state;
    private String sign;
    private String qrCode;


}
