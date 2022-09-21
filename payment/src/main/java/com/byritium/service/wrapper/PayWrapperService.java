package com.byritium.service.wrapper;

import com.byritium.constance.PaymentPattern;
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
    private static Map<PaymentPattern, QuickPayService> serviceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        serviceMap = new HashMap<>();
        Map<String, QuickPayService> map = applicationContext.getBeansOfType(QuickPayService.class);

        map.forEach((key, value) -> {
            if (value.pattern() != null)
                serviceMap.put(value.pattern(), value);
        });
    }

    @Override
    public PaymentPattern pattern() {
        return null;
    }

    @Override
    public PaymentResult pay(String businessOrderId, String subject, BigDecimal payAmount, PaymentExtra paymentExtra) {
        PaymentPattern paymentPattern = paymentExtra.getPaymentPattern();

        Assert.notNull(paymentPattern, "未选择支付渠道");

        QuickPayService payService = serviceMap.get(paymentPattern);
        if (payService == null) {
            throw new BusinessException(paymentPattern.getMessage() + "暂不开放");
        }

        return payService.pay(businessOrderId, subject, payAmount, paymentExtra);
    }
}
