package com.byritium.service;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;

public interface QueryService {
    PaymentPattern channel();

    PaymentResult query(String businessOrderId, PaymentExtra paymentExtra);
}
