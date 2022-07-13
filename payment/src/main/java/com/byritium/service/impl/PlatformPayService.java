package com.byritium.service.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;
import com.byritium.service.PayService;
import com.byritium.service.RefundService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PlatformPayService implements PayService, RefundService {

    @Override
    public PaymentChannel channel() {
        return PaymentChannel.PLATFORM_PAY;
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
