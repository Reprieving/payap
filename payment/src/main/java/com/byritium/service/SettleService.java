package com.byritium.service;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentExtra;

public interface SettleService {
    PaymentPattern channel();

    void settle(String businessOrderId, PaymentExtra paymentExtra);
}
