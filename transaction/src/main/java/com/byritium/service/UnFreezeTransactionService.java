package com.byritium.service;

import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import org.springframework.stereotype.Service;

@Service
public class UnFreezeTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.UNFREEZE;
    }

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        return null;
    }
}
