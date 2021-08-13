package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PaymentExtra;

import java.math.BigDecimal;

public interface WithdrawService {
    PaymentChannel channel();

    void withdraw(String businessOrderId, String userId, BigDecimal amount, PaymentExtra paymentExtra);
}
