package com.byritium.service.transaction;

import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TransactionManagerService implements ApplicationContextAware{
    private static Map<TransactionType, ITransactionCallService> transactionServiceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        transactionServiceMap = new HashMap<>();
        Map<String, ITransactionCallService> map = applicationContext.getBeansOfType(ITransactionCallService.class);
        map.forEach((key, value) -> {
            if (value.type() != null)
                transactionServiceMap.put(value.type(), value);
        });
    }

    public TransactionResult call(String clientId, TransactionParam param) {
        return transactionServiceMap.get(param.getTransactionType()).call(clientId, param);
    }

    public TransactionResult pay(String clientId, TransactionParam param) {
        return null;
    }


}
