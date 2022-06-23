package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import lombok.Data;

@Data
public class PaymentResult {
    private String transactionOrderId;
    private PaymentChannel paymentChannel;
    private String paymentOrderId;
    private PaymentState state;
    private String sign;
}
