package com.byritium.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VirtualCurrency {
    private Long id;
    private BigDecimal amount;
}
