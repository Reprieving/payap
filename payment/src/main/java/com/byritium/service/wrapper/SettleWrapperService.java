package com.byritium.service.wrapper;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentExtra;
import com.byritium.service.SettleService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Component
public class SettleWrapperService implements ApplicationContextAware, SettleService {

    private static Map<PaymentPattern, SettleService> serviceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        serviceMap = new HashMap<>();
        Map<String, SettleService> map = applicationContext.getBeansOfType(SettleService.class);

        map.forEach((key, value) -> {
            if (value.channel() != null)
                serviceMap.put(value.channel(), value);
        });
    }

    @Override
    public PaymentPattern channel() {
        return null;
    }

    @Override
    public void settle(String businessOrderId, PaymentExtra paymentExtra) {
        PaymentPattern paymentPattern = paymentExtra.getPaymentPattern();

        Assert.notNull(paymentPattern, "未选择支付渠道");

        SettleService settleService = serviceMap.get(paymentPattern);

        settleService.settle(businessOrderId, paymentExtra);
    }


}
