package com.byritium.dto;

import com.byritium.constance.PaymentState;
import lombok.Data;

@Data
public class PaymentResult {
    private String transactionOrderId;
    private String paymentOrderId;
    private PaymentState state;
    private String sign;
}
