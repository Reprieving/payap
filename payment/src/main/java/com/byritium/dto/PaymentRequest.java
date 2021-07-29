package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.constance.TransactionProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class PaymentRequest {
    private String clientId;
    private String businessOrderId;
    private String subject;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private PaymentChannel paymentChannel;
    private TransactionProduct transactionProduct;
    private PaymentProduct paymentProduct;
    private Timestamp createTime;
}
