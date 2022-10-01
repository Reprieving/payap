package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.IdContainer;
import com.byritium.dto.PaymentExtra;
import com.byritium.dto.PaymentResult;

import java.math.BigDecimal;

public interface PayService {

    PaymentChannel channel();

    PaymentResult pay(IdContainer idContainer, String subject, BigDecimal orderAmount);

}
