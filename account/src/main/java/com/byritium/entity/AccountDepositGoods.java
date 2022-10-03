package com.byritium.entity;

import com.byritium.constance.ClientType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDepositGoods extends CommonEntity{
    private String id;
    private ClientType client;
    private BigDecimal price;
    private BigDecimal amount;

}
