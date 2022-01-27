package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountAuthTemplate extends CommonEntity{
    private String id;
    private String typeId;
    private Boolean payPermit;
    private Boolean rechargePermit;
    private Boolean withdrawPermit;
    private Boolean transferPermit;
    private LocalDateTime crateTime;

    public AccountAuth toAuth(String entityId) {
        AccountAuth accountAuth = new AccountAuth();
        BeanUtils.copyProperties(this, accountAuth);
        accountAuth.setEntityId(entityId);
        return accountAuth;
    }
}
