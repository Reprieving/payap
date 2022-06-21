package com.byritium.service.transaction;

import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;

public interface ITransactionCallBackService {
    TransactionResult callback(String clientId, TransactionParam param);


}
