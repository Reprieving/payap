package com.byritium.entity;


import com.byritium.constance.AccountIdType;
import com.byritium.constance.AccountPrimaryStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AccountCore {
    private String id;
    private String objectId;
    private AccountIdType idType;
    private Timestamp crateTime;
    private AccountPrimaryStatus status;
}
