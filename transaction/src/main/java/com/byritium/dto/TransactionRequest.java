package com.byritium.dto;


import com.byritium.constance.PaymentChannel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransactionRequest {
    private String businessOrderId;
    private String userId;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private String subject;
    private PaymentChannel paymentChannel;
    private List<String> couponList;
    private List<Deduction> deductionList;


}
