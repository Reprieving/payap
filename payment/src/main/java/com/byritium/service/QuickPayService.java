package com.byritium.service;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;

import java.math.BigDecimal;

public interface QuickPayService {
    PaymentPattern pattern();

    String key();

    PaymentResult pay(String businessOrderId, String subject, BigDecimal payAmount, PaymentExtra paymentExtra);

}
