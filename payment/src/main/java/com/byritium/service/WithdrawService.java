package com.byritium.service;

import java.math.BigDecimal;

public interface WithdrawService {
    void withdraw(String businessOrderId, String userId, BigDecimal amount);
}
