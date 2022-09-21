package com.byritium.service.impl;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;
import com.byritium.service.QuickPayService;
import com.byritium.service.RefundService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PlatformPayService implements QuickPayService, RefundService {

    @Override
    public PaymentPattern pattern() {
        return PaymentPattern.ACCOUNT_PAY;
    }

    @Override
    public PaymentResult pay(String businessOrderId, String subject, BigDecimal orderAmount, PaymentExtra paymentExtra) {
        //调用清算服务


        return null;
    }

    @Override
    public void refund(String businessOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra) {

    }

}
