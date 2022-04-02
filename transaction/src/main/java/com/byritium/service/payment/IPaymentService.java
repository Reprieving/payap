package com.byritium.service.payment;

import com.byritium.constance.PaymentType;
import com.byritium.dto.PaymentResult;
import com.byritium.entity.PayOrder;

public interface IPaymentService {
    PaymentType type();

    PaymentResult call(PayOrder payOrder);

}
