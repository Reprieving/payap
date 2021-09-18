package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AccountSdk {
    private String id;
    private String typeId;
    private String sdkId;
    private String sdkAppId;
    private Timestamp crateTime;
}
