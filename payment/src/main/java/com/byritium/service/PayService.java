package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;

import java.math.BigDecimal;

public interface PayService {
    PaymentProduct product();

    PaymentChannel channel();

    PayParam pay(String businessOrderId, String subject, BigDecimal payAmount, PaymentExtra paymentExtra);

}
