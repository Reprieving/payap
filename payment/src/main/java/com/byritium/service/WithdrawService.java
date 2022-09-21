package com.byritium.service;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentExtra;

import java.math.BigDecimal;

public interface WithdrawService {
    PaymentPattern channel();

    void withdraw(String businessOrderId, String userId, BigDecimal amount, PaymentExtra paymentExtra);
}
