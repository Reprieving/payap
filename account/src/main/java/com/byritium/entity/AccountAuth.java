package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountAuth {
    private String id;
    private String entityId;
    private Boolean payPermit;
    private Boolean rechargePermit;
    private Boolean withdrawPermit;
    private Boolean transferPermit;
    private LocalDateTime crateTime;
}
