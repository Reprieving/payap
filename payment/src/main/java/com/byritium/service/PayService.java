package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.IdContainer;
import com.byritium.dto.PaymentExtra;
import com.byritium.dto.PaymentResult;
import com.byritium.entity.payment.PaymentPattern;

import java.math.BigDecimal;

public interface PayService {

    PaymentChannel channel();

    PaymentResult pay(PaymentPattern pattern, IdContainer idContainer, String subject, BigDecimal orderAmount);

}
