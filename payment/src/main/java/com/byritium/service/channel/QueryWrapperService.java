package com.byritium.service.channel;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PayParam;
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
    private static Map<PaymentChannel, QueryService> serviceMap;

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
    public PaymentChannel channel() {
        return null;
    }

    @Override
    public PayParam query(String businessOrderId, PaymentExtra paymentExtra) {
        PaymentChannel paymentChannel = paymentExtra.getPaymentChannel();

        Assert.notNull(paymentChannel, "未选择支付渠道");

        QueryService queryService = serviceMap.get(paymentChannel);
        if (queryService == null) {
            throw new BusinessException(paymentChannel.getMessage() + "暂不开放");
        }

        return queryService.query(businessOrderId, paymentExtra);
    }

}
