package com.byritium.service;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentExtra;
import com.byritium.dto.PaymentResult;

import java.math.BigDecimal;

public interface FacePayService {
    PaymentPattern channel();

    PaymentResult pay(String businessOrderId, String subject, BigDecimal payAmount, PaymentExtra paymentExtra);

}
