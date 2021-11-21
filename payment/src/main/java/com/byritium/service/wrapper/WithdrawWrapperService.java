package com.byritium.service.wrapper;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PaymentExtra;
import com.byritium.exception.BusinessException;
import com.byritium.service.WithdrawService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class WithdrawWrapperService implements ApplicationContextAware, WithdrawService {
    private static Map<PaymentChannel, WithdrawService> serviceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        serviceMap = new HashMap<>();
        Map<String, WithdrawService> map = applicationContext.getBeansOfType(WithdrawService.class);

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
    public void withdraw(String businessOrderId, String userId, BigDecimal amount, PaymentExtra paymentExtra) {
        PaymentChannel paymentChannel = paymentExtra.getPaymentChannel();

        Assert.notNull(paymentChannel, "未选择支付渠道");

        WithdrawService withdrawService = serviceMap.get(paymentChannel);
        if (withdrawService == null) {
            throw new BusinessException(paymentChannel.getMessage() + "暂不开放");
        }

        withdrawService.withdraw(businessOrderId, userId, amount, paymentExtra);
    }
}
