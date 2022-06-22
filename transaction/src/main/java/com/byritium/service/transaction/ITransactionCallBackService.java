package com.byritium.service.transaction;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.TransactionResult;

public interface ITransactionCallBackService {
    TransactionResult paymentCallback(PaymentResult paymentResult);

    TransactionResult refundCallback(PaymentResult paymentResult);
}
