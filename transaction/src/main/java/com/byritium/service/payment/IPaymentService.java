package com.byritium.service.payment;

import com.byritium.constance.PaymentType;
import com.byritium.dto.PaymentResult;
import com.byritium.entity.TransactionPaymentOrder;

public interface IPaymentService {
    PaymentType type();

    PaymentResult call(TransactionPaymentOrder transactionPaymentOrder);

}
