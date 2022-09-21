package com.byritium.service.wrapper;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;
import com.byritium.exception.BusinessException;
import com.byritium.service.QueryService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Service
public class QueryWrapperService implements ApplicationContextAware, QueryService {
    private static Map<PaymentPattern, QueryService> serviceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        serviceMap = new HashMap<>();
        Map<String, QueryService> map = applicationContext.getBeansOfType(QueryService.class);

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
    public PaymentResult query(String businessOrderId, PaymentExtra paymentExtra) {
        PaymentPattern paymentPattern = paymentExtra.getPaymentPattern();

        Assert.notNull(paymentPattern, "未选择支付渠道");

        QueryService queryService = serviceMap.get(paymentPattern);
        if (queryService == null) {
            throw new BusinessException(paymentPattern.getMessage() + "暂不开放");
        }

        return queryService.query(businessOrderId, paymentExtra);
    }

}
