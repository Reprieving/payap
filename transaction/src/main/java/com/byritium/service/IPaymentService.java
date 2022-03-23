package com.byritium.service;

import com.byritium.constance.PaymentType;
import com.byritium.constance.TransactionType;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionPaymentOrder;

public interface IPaymentService {
    PaymentType type();

    PaymentResult call(TransactionPaymentOrder transactionPaymentOrder);

}
