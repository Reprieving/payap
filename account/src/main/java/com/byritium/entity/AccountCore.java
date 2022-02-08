package com.byritium.entity;


import com.byritium.constance.AccountIdType;
import com.byritium.constance.AccountState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter
@Setter
public class AccountCore extends CommonEntity {
    @Id
    private String id;
    private String userId;
    private AccountIdType idType;
    private AccountState state;
}
