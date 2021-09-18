package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AccountType {
    private String id;
    private String coreId;
    private String type;
    private Timestamp crateTime;
}
