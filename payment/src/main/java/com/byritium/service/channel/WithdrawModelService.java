package com.byritium.service.channel;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import com.byritium.exception.BusinessException;
import com.byritium.service.PayService;
import com.byritium.service.WithdrawService;
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
public class WithdrawModelService implements ApplicationContextAware, WithdrawService {
    private static Table<PaymentProduct, PaymentChannel, WithdrawService> withdrawServiceTable;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        withdrawServiceTable = HashBasedTable.create();
        Map<String, WithdrawService> map = applicationContext.getBeansOfType(WithdrawService.class);

        map.forEach((key, value) -> {
            if (value.product() != null && value.channel() != null)
                withdrawServiceTable.put(value.product(), value.channel(), value);
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
    public void withdraw(String businessOrderId, String userId, BigDecimal amount, PaymentExtra paymentExtra) {
        PaymentProduct paymentProduct = paymentExtra.getPaymentProduct();
        PaymentChannel paymentChannel = paymentExtra.getPaymentChannel();

        Assert.notNull(paymentProduct, "未选择支付产品");
        Assert.notNull(paymentChannel, "未选择支付渠道");

        WithdrawService withdrawService = withdrawServiceTable.get(paymentProduct, paymentChannel);
        if (withdrawService == null) {
            throw new BusinessException(paymentProduct.getMessage() + "-" + paymentChannel.getMessage() + "暂不开放");
        }

        withdrawService.withdraw(businessOrderId, userId, amount, paymentExtra);
    }
}
