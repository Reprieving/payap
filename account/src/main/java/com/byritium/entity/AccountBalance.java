package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class AccountBalance {
    private String id;
    private String typeId;
    private BigDecimal total;
    private BigDecimal free;
    private BigDecimal frozen;
    private Timestamp crateTime;
}
