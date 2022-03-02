package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountBalance extends CommonEntity{
    private String id;
    private String entityId;
    private BigDecimal total;
    private BigDecimal free;
    private BigDecimal frozen;

    public AccountBalance() {
    }

    public AccountBalance(AccountEntity accountEntity) {
        this.entityId = accountEntity.getId();
        this.createTime = accountEntity.getCreateTime();
    }
}
