package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String businessEncrypt;
    private String businessOrderId;
    private String userId;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private String subject;

    private PaymentChannel paymentChannel;
}
