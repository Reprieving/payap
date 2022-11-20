package com.byritium.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentParam {
    private long bizOrderId;
    private long txOrderId;
    private long uid;
    private long paymentPatternId;
    private String subject;
    private BigDecimal orderAmount;
}
