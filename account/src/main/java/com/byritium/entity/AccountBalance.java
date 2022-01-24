package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountBalance {
    private String id;
    private String entityId;
    private BigDecimal total;
    private BigDecimal free;
    private BigDecimal frozen;
    private LocalDateTime crateTime;

    public AccountBalance() {
    }

    public AccountBalance(AccountEntity accountEntity) {
        this.entityId = accountEntity.getId();
        this.crateTime = accountEntity.getCrateTime();
    }
}
