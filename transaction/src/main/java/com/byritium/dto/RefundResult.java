package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefundResult extends PaymentResult{
    private String transactionOrderId;
    private PaymentChannel paymentChannel;
    private String paymentOrderId;
    private String refundOrderId;
}
