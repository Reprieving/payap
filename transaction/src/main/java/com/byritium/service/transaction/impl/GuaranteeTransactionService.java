package com.byritium.service.transaction.impl;

import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.TransactionOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class GuaranteeTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.GUARANTEE;
    }

    @Resource
    private TransactionOrderService transactionOrderService;

    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = transactionOrderService.trade(clientId, param);

        return transactionResult;
    }

    public TransactionResult call() {
        return null;
    }

}
