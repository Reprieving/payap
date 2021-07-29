package com.byritium.service.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import com.byritium.service.PayService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountPayService implements PayService {

    @Override
    public PaymentProduct product() {
        return PaymentProduct.ACCOUNT_PAY;
    }

    @Override
    public PaymentChannel channel() {
        return PaymentChannel.ACCOUNT_PAY;
    }

    @Override
    public PayParam pay(String businessOrderId, String subject, BigDecimal orderAmount, PaymentExtra paymentExtra) {
        //调用清算服务，确定扣款人id和账户，收款人id和账户


        //扣款


        return null;
    }


}
