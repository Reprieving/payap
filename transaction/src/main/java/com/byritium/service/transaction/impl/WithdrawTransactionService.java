package com.byritium.service.transaction.impl;

import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.service.transaction.ITransactionService;
import org.springframework.stereotype.Service;

@Service
public class WithdrawTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.WITHDRAW;
    }

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        return null;
    }
}
