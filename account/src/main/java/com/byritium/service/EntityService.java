package com.byritium.service;

import com.byritium.entity.*;
import com.byritium.respository.AuthRepository;
import com.byritium.respository.BalanceRepository;
import com.byritium.respository.EntityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EntityService {

    @Resource
    private EntityRepository entityRepository;

    @Resource
    private BalanceRepository balanceRepository;

    @Resource
    private AuthRepository authRepository;


    public void create(String coreId, AccountEntityType accountEntityType, AccountAuthTemplate accountAuthTemplate) {
        AccountEntity accountEntity = new AccountEntity(coreId, accountEntityType);
        entityRepository.save(accountEntity);

        AccountBalance accountBalance = new AccountBalance(accountEntity);
        balanceRepository.save(accountBalance);

        AccountAuth accountAuth = accountAuthTemplate.toAuth(accountEntity.getId());
        authRepository.save(accountAuth);
    }
}
