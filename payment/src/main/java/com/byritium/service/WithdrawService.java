package com.byritium.service;

import com.byritium.constance.PaymentChannel;

import java.math.BigDecimal;

public interface WithdrawService {
    PaymentChannel channel();

    void withdraw(String businessOrderId, String userId, BigDecimal amount);
}
