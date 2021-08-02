package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PaymentExtra;

import java.math.BigDecimal;

public interface RefundService {
    PaymentProduct product();

    PaymentChannel channel();

    void refund(String businessOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra);
}
