package com.byritium.dto.transaction;

import com.byritium.dto.VirtualCurrency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundParam {
    private Long clientId;
    private Long bizOrderId;
    private Long uid;
    private BigDecimal amount;
    private String subject;
}
