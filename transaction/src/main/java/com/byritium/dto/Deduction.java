package com.byritium.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
@Setter
public class Deduction {
    private String name;
    private String accountTypeId;
    private BigDecimal amount;
}