package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import org.springframework.stereotype.Service;

public interface QueryService {
    PaymentChannel channel();

    PayParam query(String businessOrderId, PaymentExtra paymentExtra);
}
