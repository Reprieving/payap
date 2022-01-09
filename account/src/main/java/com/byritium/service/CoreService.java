package com.byritium.service;

import com.byritium.entity.AccountCore;
import com.byritium.entity.AccountEntity;
import com.byritium.entity.AccountEntityType;
import com.byritium.respository.CoreRepository;
import com.byritium.respository.EntityRepository;
import com.byritium.respository.EntityTypeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CoreService {

    @Resource
    private CoreRepository coreRepository;

    @Resource
    private EntityRepository entityRepository;

    @Resource
    private EntityTypeRepository entityTypeRepository;

    public void register(AccountCore accountCore) {
        coreRepository.save(accountCore);

        String coreId = accountCore.getId();
        List<AccountEntityType> accountEntityTypeList = entityTypeRepository.findByInitFlag(true);
        for (AccountEntityType accountEntityType : accountEntityTypeList) {
            AccountEntity accountEntity = new AccountEntity(coreId, accountEntityType);
            entityRepository.save(accountEntity);
        }
    }


}
