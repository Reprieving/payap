package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PaymentExtra;

import java.math.BigDecimal;

public interface SettleService {
    PaymentProduct product();

    PaymentChannel channel();

    void settle(String businessOrderId, PaymentExtra paymentExtra);
}
