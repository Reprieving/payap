package com.byritium.entity;

import com.byritium.constance.AccountState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountEntity extends CommonEntity{
    @Id
    private String id;
    private String pid;
    private String coreId;
    private String accountTypeId;
    private String accountName;
    private AccountState state;

    public AccountEntity() {
    }

    public AccountEntity(String coreId, AccountEntityType accountEntityType) {
        this.coreId = coreId;
        this.accountTypeId = accountEntityType.getId();
        this.accountName = accountEntityType.getTypeName();
        this.state = AccountState.NORMAL;
        this.crateTime = LocalDateTime.now();
    }
}
