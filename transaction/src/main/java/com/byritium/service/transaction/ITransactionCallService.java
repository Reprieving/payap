package com.byritium.service.transaction;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.transaction.TransactionPayOrder;

import java.util.List;

public interface ITransactionCallService {
    TransactionType type();

    TransactionResult call(String clientId, TransactionParam param);

}
