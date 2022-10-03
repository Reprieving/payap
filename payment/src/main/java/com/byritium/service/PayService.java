package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.ClientInfo;
import com.byritium.dto.IdContainer;
import com.byritium.dto.PaymentResult;
import com.byritium.entity.payment.PaymentSetting;

import java.math.BigDecimal;

public interface PayService {

    PaymentChannel channel();

    PaymentResult pay(ClientInfo clientInfo, PaymentSetting setting, IdContainer idContainer, String subject, BigDecimal orderAmount);

}
