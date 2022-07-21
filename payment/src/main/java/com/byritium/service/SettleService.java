package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PaymentExtra;

public interface SettleService {
    PaymentChannel channel();

    void settle(String businessOrderId, PaymentExtra paymentExtra);
}
