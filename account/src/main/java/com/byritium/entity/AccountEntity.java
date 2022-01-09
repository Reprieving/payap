package com.byritium.entity;

import com.byritium.constance.AccountState;
import com.byritium.constance.AccountType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter
@Setter
public class AccountEntity {
    @Id
    private String id;
    private String pid;
    private String coreId;
    private String accountTypeId;
    private String accountName;
    private AccountState state;
    private Timestamp crateTime;

    public AccountEntity() {
    }

    public AccountEntity(String coreId, AccountEntityType accountEntityType) {
        this.coreId = coreId;
        this.accountTypeId = accountEntityType.getId();
        this.accountName = accountEntityType.getTypeName();
        this.state = AccountState.NORMAL;
        this.crateTime = new Timestamp(System.currentTimeMillis());
    }
}
