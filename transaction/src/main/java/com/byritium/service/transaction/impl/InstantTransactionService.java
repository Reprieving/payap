package com.byritium.service.transaction.impl;

import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.service.transaction.ITransactionCallService;
import com.byritium.service.transaction.order.TransactionOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class InstantTransactionService implements ITransactionCallService {
    @Override
    public TransactionType type() {
        return TransactionType.INSTANT;
    }

    @Resource
    private TransactionOrderService transactionOrderService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = transactionOrderService.trade(clientId, param);
        return transactionResult;
    }
}
