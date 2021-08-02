package com.byritium.service.channel;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PaymentExtra;
import com.byritium.exception.BusinessException;
import com.byritium.service.PayService;
import com.byritium.service.RefundService;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class RefundModelService implements ApplicationContextAware, RefundService {
    private static Table<PaymentProduct, PaymentChannel, RefundService> refundServiceTable;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        refundServiceTable = HashBasedTable.create();
        Map<String, RefundService> map = applicationContext.getBeansOfType(RefundService.class);

        map.forEach((key, value) -> {
            if (value.product() != null && value.channel() != null)
                refundServiceTable.put(value.product(), value.channel(), value);
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
    public void refund(String businessOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra) {
        PaymentProduct paymentProduct = paymentExtra.getPaymentProduct();
        PaymentChannel paymentChannel = paymentExtra.getPaymentChannel();

        Assert.notNull(paymentProduct, "未选择支付产品");
        Assert.notNull(paymentChannel, "未选择支付渠道");

        RefundService refundService = refundServiceTable.get(paymentProduct, paymentChannel);
        if (refundService == null) {
            throw new BusinessException(paymentProduct.getMessage() + "-" + paymentChannel.getMessage() + "暂不开放");
        }

        refundService.refund(businessOrderId, refundOrderId, orderAmount, refundAmount, paymentExtra);
    }


}
