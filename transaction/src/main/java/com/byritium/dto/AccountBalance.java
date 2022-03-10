package com.byritium.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBalance {
    private BigDecimal amount;//余额
    private BigDecimal value;//余额面值
}
