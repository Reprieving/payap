package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;

import java.math.BigDecimal;

public interface QuickPayService {
    PaymentChannel channel();

    PaymentResult pay(String businessOrderId, String subject, BigDecimal payAmount, PaymentExtra paymentExtra);

}
