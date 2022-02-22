package com.byritium.service;

import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;

public interface ITransactionService {
    TransactionType type();

    TransactionResult call(String clientId, TransactionParam param);
}
