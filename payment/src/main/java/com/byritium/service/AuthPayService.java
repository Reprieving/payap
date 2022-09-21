package com.byritium.service;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentExtra;
import com.byritium.dto.PaymentResult;

import java.math.BigDecimal;

public interface AuthPayService {
    PaymentPattern channel();

    PaymentResult pay(String businessOrderId, String subject, BigDecimal payAmount, PaymentExtra paymentExtra);

    void freeze(long bizOrderNo, long trxOrderNo, long authOrderNo, String title, BigDecimal amount);

    void pay(long authOrderNo, long payOrderNo, String title, BigDecimal amount);

    void unfreeze(long authOrderNo, long payOrderNo, String title, BigDecimal amount);
}
