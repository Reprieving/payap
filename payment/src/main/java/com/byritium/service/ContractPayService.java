package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PaymentExtra;
import com.byritium.dto.PaymentResult;

import java.math.BigDecimal;

public interface ContractPayService {
    PaymentChannel channel();

    PaymentResult pay(String businessOrderId, String subject, BigDecimal payAmount, PaymentExtra paymentExtra);

}
