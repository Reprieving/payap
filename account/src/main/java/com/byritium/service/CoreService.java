package com.byritium.service;


import com.byritium.entity.*;
import com.byritium.respository.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoreService {

    @Resource
    private EntityService entityService;

    @Resource
    private CoreRepository coreRepository;

    @Resource
    private EntityTypeRepository entityTypeRepository;

    @Resource
    private AuthTemplateRepository authTemplateRepository;

    public void register(AccountCore accountCore) {
        List<AccountEntityType> accountEntityTypeList = entityTypeRepository.findByInitFlag(true);
        Iterable<AccountAuthTemplate> accountAuthTemplates = authTemplateRepository.findAll();
        Map<String, AccountAuthTemplate> map = new HashMap<>(20);
        accountAuthTemplates.forEach(accountAuthTemplate -> map.put(accountAuthTemplate.getTypeId(), accountAuthTemplate));

        coreRepository.save(accountCore);
        String coreId = accountCore.getId();

        for (AccountEntityType accountEntityType : accountEntityTypeList) {
            AccountAuthTemplate accountAuthTemplate = map.get(accountEntityType.getId());
            entityService.create(coreId, accountEntityType, accountAuthTemplate);
        }
    }


}
