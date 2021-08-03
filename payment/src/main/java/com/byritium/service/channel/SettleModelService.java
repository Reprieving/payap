package com.byritium.service.channel;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PaymentExtra;
import com.byritium.exception.BusinessException;
import com.byritium.service.QueryService;
import com.byritium.service.SettleService;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class SettleModelService implements ApplicationContextAware, SettleService {

    private static Table<PaymentProduct, PaymentChannel, SettleService> settleServiceTable;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        settleServiceTable = HashBasedTable.create();
        Map<String, SettleService> map = applicationContext.getBeansOfType(SettleService.class);

        map.forEach((key, value) -> {
            if (value.product() != null && value.channel() != null)
                settleServiceTable.put(value.product(), value.channel(), value);
        });
    }

    @Override
    public PaymentProduct product() {
        return null;
    }

    @Override
    public PaymentChannel channel() {
        return null;
    }

    @Override
    public void settle(String businessOrderId, PaymentExtra paymentExtra) {
        PaymentProduct paymentProduct = paymentExtra.getPaymentProduct();
        PaymentChannel paymentChannel = paymentExtra.getPaymentChannel();

        Assert.notNull(paymentProduct, "未选择支付产品");
        Assert.notNull(paymentChannel, "未选择支付渠道");

        SettleService settleService = settleServiceTable.get(paymentProduct, paymentChannel);
        if (settleService == null) {
            throw new BusinessException(paymentProduct.getMessage() + "-" + paymentChannel.getMessage() + "结算暂不开放");
        }

        settleService.settle(businessOrderId, paymentExtra);
    }


}
