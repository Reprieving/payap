package com.byritium.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.byritium.constance.BaseConst;
import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.AliPayConfig;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import com.byritium.exception.BusinessException;
import com.byritium.service.PayService;
import com.byritium.service.RefundService;
import com.byritium.service.WithdrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Service
@Slf4j
public class AliPayAppService extends AliPayService implements PayService, RefundService, WithdrawService {
    @Override
    public PaymentProduct product() {
        return PaymentProduct.PLATFORM_ONLINE_PAY;
    }

    @Override
    public PaymentChannel channel() {
        return PaymentChannel.ALI_PAY;
    }

    @Override
    public PayParam pay(String businessOrderId, String subject, BigDecimal orderAmount, PaymentExtra paymentExtra) {
        String notifyUrl = BaseConst.GATEWAY_URL.concat("/transaction/alipay/pay");
        AliPayConfig aliPayConfig = new AliPayConfig();

        CertAlipayRequest certAlipayRequest = buildRequest(aliPayConfig);

        AlipayClient alipayClient;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.transaction
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setSubject(subject);
            model.setOutTradeNo(businessOrderId);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(orderAmount.toPlainString());
            model.setProductCode("QUICK_MSECURITY_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(BaseConst.ALIPAY_NOTICE_URL);

            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String body = response.getBody();
            Assert.state(!StringUtils.hasText(body), "支付宝签名失败");

            PayParam payParam = new PayParam();
            payParam.setSign(body);

            return payParam;
        } catch (AlipayApiException e) {
            log.error("支付宝渠道支付失败，支付订单id：{}", businessOrderId, e);
            throw new BusinessException("支付宝渠道支付失败");
        }
    }
}
