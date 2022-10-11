package com.byritium.service.transaction;

import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;

public interface ITransactionCallService {
    TransactionType type();

    TransactionResult call(String clientId, TransactionParam param);

}
