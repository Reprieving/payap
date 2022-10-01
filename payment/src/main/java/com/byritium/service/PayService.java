package com.byritium.service;

import com.byritium.dto.IdContainer;
import com.byritium.dto.PaymentExtra;
import com.byritium.dto.PaymentResult;

import java.math.BigDecimal;

public interface PayService {

    String key();

    PaymentResult pay(IdContainer idContainer, String subject, BigDecimal orderAmount);

}
