package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentExtra;

import java.math.BigDecimal;

public interface RefundService {
    PaymentChannel channel();

    void refund(String businessOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra);
}
