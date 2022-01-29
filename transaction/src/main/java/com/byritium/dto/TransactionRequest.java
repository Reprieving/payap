package com.byritium.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TransactionRequest {
    private String businessEncrypt;
    private String businessOrderId;
    private String userId;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private String subject;
    private List<Deduction> deductionList;

}
