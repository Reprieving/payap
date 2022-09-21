package com.byritium.service.wrapper;

import com.byritium.constance.PaymentPattern;
import com.byritium.dto.PaymentExtra;
import com.byritium.exception.BusinessException;
import com.byritium.service.RefundService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class RefundWrapperService implements ApplicationContextAware, RefundService {
    private static Map<PaymentPattern, RefundService> serviceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        serviceMap = new HashMap<>();
        Map<String, RefundService> map = applicationContext.getBeansOfType(RefundService.class);

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
    public void refund(String businessOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra) {
        PaymentPattern paymentPattern = paymentExtra.getPaymentPattern();

        Assert.notNull(paymentPattern, "未选择支付渠道");

        RefundService refundService = serviceMap.get(paymentPattern);
        if (refundService == null) {
            throw new BusinessException(paymentPattern.getMessage() + "暂不开放");
        }

        refundService.refund(businessOrderId, refundOrderId, orderAmount, refundAmount, paymentExtra);
    }


}
