package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class AccountAuth {
    private String id;
    private String typeId;
    private Boolean payPermit;
    private Boolean rechargePermit;
    private Boolean withdrawPermit;
    private Boolean transferPermit;
    private Timestamp crateTime;
}
