package com.byritium.service.impl;

import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class WechatPayOnlineService extends WechatPayService {
    @Override
    public PayParam pay(String businessOrderId, String subject, BigDecimal orderAmount, PaymentExtra paymentExtra) {
        return null;
    }
}
