package com.byritium.service.wrapper;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;
import com.byritium.exception.BusinessException;
import com.byritium.service.QuickPayService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayWrapperService implements ApplicationContextAware, QuickPayService {
    private static Map<PaymentChannel, QuickPayService> serviceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        serviceMap = new HashMap<>();
        Map<String, QuickPayService> map = applicationContext.getBeansOfType(QuickPayService.class);

        map.forEach((key, value) -> {
            if (value.channel() != null)
                serviceMap.put(value.channel(), value);
        });
    }

    @Override
    public PaymentChannel channel() {
        return null;
    }

    @Override
    public PaymentResult pay(String businessOrderId, String subject, BigDecimal payAmount, PaymentExtra paymentExtra) {
        PaymentChannel paymentChannel = paymentExtra.getPaymentChannel();

        Assert.notNull(paymentChannel, "未选择支付渠道");

        QuickPayService payService = serviceMap.get(paymentChannel);
        if (payService == null) {
            throw new BusinessException(paymentChannel.getMessage() + "暂不开放");
        }

        return payService.pay(businessOrderId, subject, payAmount, paymentExtra);
    }
}
