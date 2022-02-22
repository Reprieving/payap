package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionOrderRepository;
import com.byritium.dto.Deduction;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPayOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionService implements ApplicationContextAware, ITransactionService {

    private static Map<TransactionType, ITransactionService> transactionServiceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        transactionServiceMap = new HashMap<>();
        Map<String, ITransactionService> map = applicationContext.getBeansOfType(ITransactionService.class);

        map.forEach((key, value) -> {
            if (value.type() != null)
                transactionServiceMap.put(value.type(), value);
        });
    }

    @Override
    public TransactionType type() {
        return null;
    }

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        return transactionServiceMap.get(param.getTransactionType()).call(clientId, param);
    }


}
