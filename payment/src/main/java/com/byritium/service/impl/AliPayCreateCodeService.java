package com.byritium.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.byritium.constance.BaseConst;
import com.byritium.constance.PaymentChannel;
import com.byritium.dto.AliPayConfig;
import com.byritium.dto.PaymentExtra;
import com.byritium.dto.PaymentResult;
import com.byritium.exception.BusinessException;
import com.byritium.service.QuickPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Service
@Slf4j
public class AliPayCreateCodeService extends AliPayService implements QuickPayService {

    @Override
    public PaymentChannel channel() {
        return PaymentChannel.ALI_PAY_CREATE_CODE;
    }

    @Override
    public PaymentResult pay(String businessOrderId, String subject, BigDecimal orderAmount, PaymentExtra paymentExtra) {
        AliPayConfig aliPayConfig = new AliPayConfig();

        CertAlipayRequest certAlipayRequest = buildRequest(aliPayConfig);

        AlipayClient alipayClient;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
            AlipayTradePayRequest request = new AlipayTradePayRequest();
            AlipayTradePayModel model = new AlipayTradePayModel();
            model.setSubject(subject);
            model.setOutTradeNo(businessOrderId);
            model.setTotalAmount(orderAmount.toPlainString());
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(BaseConst.ALIPAY_NOTICE_URL);

            AlipayTradePayResponse response = alipayClient.pageExecute(request);
            String body = response.getBody();
            Assert.state(!StringUtils.hasText(body), "支付宝签名失败");

            PaymentResult paymentResult = new PaymentResult();
            paymentResult.setPrePayId(body);

            return paymentResult;
        } catch (AlipayApiException e) {
            log.error("支付宝渠道支付失败，支付订单id：{}", businessOrderId, e);
            throw new BusinessException("支付宝渠道支付失败");
        }
    }
}
