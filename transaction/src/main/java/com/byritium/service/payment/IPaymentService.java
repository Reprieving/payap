package com.byritium.service.payment;

import com.byritium.constance.PaymentType;
import com.byritium.dto.PaymentResult;
import com.byritium.entity.PaymentOrder;

public interface IPaymentService {
    PaymentType type();

    PaymentResult call(PaymentOrder paymentOrder);

}
