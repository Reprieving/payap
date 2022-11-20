package com.byritium.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayDetail {
    private Long payOrderId;
    private Long uid;
    private BigDecimal orderAmount;
    private String title;
    private boolean settleFlag;
}
