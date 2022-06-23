package com.byritium.service.transaction;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.TransactionResult;

public interface ITransactionCallBackService {
    void paymentCallback(PaymentResult paymentResult);

    void refundCallback(PaymentResult paymentResult);
}
