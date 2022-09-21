package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefundResult extends PaymentResult{
    private String transactionOrderId;
    private PaymentPattern paymentPattern;
    private String paymentOrderId;
    private String refundOrderId;
}
