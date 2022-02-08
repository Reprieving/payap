package com.byritium.entity;

import com.byritium.constance.AppClient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDepositGoods extends CommonEntity{
    private String id;
    private AppClient client;
    private BigDecimal price;
    private BigDecimal amount;

}
