package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProductType;
import com.byritium.constance.TransactionProductType;
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
    private TransactionProductType transactionProductType;
    private PaymentProductType paymentProductType;
    private Timestamp createTime;
}
