package com.byritium.service;

import com.byritium.dto.PayParam;

import java.math.BigDecimal;

public interface PayService {
    PayParam pay(String businessOrderId, String subject, BigDecimal orderAmount);

}
