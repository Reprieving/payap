package com.byritium.service.channel;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import com.byritium.exception.BusinessException;
import com.byritium.service.PayService;
import com.byritium.service.QueryService;
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
public class QueryModelService implements ApplicationContextAware, QueryService {
    private static Table<PaymentProduct, PaymentChannel, QueryService> queryServiceTable;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        queryServiceTable = HashBasedTable.create();
        Map<String, QueryService> map = applicationContext.getBeansOfType(QueryService.class);

        map.forEach((key, value) -> {
            if (value.product() != null && value.channel() != null)
                queryServiceTable.put(value.product(), value.channel(), value);
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
    public PayParam query(String businessOrderId, PaymentExtra paymentExtra) {
        PaymentProduct paymentProduct = paymentExtra.getPaymentProduct();
        PaymentChannel paymentChannel = paymentExtra.getPaymentChannel();

        Assert.notNull(paymentProduct, "未选择支付产品");
        Assert.notNull(paymentChannel, "未选择支付渠道");

        QueryService queryService = queryServiceTable.get(paymentProduct, paymentChannel);
        if (queryService == null) {
            throw new BusinessException(paymentProduct.getMessage() + "-" + paymentChannel.getMessage() + "暂不开放");
        }

        return queryService.query(businessOrderId, paymentExtra);
    }

}
