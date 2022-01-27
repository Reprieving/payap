package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AccountSdk extends CommonEntity{
    private String id;
    private String typeId;
    private String sdkId;
    private String sdkAppId;
}
