package com.byritium.service.channel;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import com.byritium.service.PayService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class PayChannelService implements ApplicationContextAware, PayService {

    private static Map<PaymentChannel, PayService> channelPayServiceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        channelPayServiceMap = new HashMap<>(10);
        Map<String, PayService> map = applicationContext.getBeansOfType(PayService.class);

        map.forEach((key, value) -> {
            if (key != null)
                channelPayServiceMap.put(value
                        .channel(), value);
        });
    }

    @Override
    public PaymentChannel channel() {
        return null;
    }

    @Override
    public PayParam pay(String businessOrderId, String subject, BigDecimal payAmount, PaymentExtra paymentExtra) {
        PaymentChannel paymentChannel = paymentExtra.getPaymentChannel();
        PayService payService = channelPayServiceMap.get(paymentChannel);
        if (payService == null) {
            //TODO throw exception
            System.out.println(paymentChannel.getMessage() + "暂不开放");
        }

        return payService.pay(businessOrderId, subject, payAmount, paymentExtra);
    }
}
