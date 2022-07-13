package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;

public interface QueryService {
    PaymentChannel channel();

    PaymentResult query(String businessOrderId, PaymentExtra paymentExtra);
}
