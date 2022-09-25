package com.byritium.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundAuthOrderFreezeModel;
import com.alipay.api.request.AlipayFundAuthOrderFreezeRequest;
import com.alipay.api.response.AlipayFundAuthOrderFreezeResponse;
import com.byritium.constance.BaseConst;
import com.byritium.constance.PaymentPattern;
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
public class AliPayAuthOffLineScanService extends AliPayService implements QuickPayService {

    @Override
    public String key() {
        return null;
    }

    @Override
    public PaymentResult pay(String businessOrderId, String subject, BigDecimal orderAmount, PaymentExtra paymentExtra) {
        AliPayConfig aliPayConfig = new AliPayConfig();

        CertAlipayRequest certAlipayRequest = buildRequest(aliPayConfig);

        AlipayClient alipayClient;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
            AlipayFundAuthOrderFreezeRequest request = new AlipayFundAuthOrderFreezeRequest  ();
            AlipayFundAuthOrderFreezeModel model = new AlipayFundAuthOrderFreezeModel();
            model.setAuthCode(paymentExtra.getAutoCode());
            model.setAuthCodeType("bar_code");
            model.setOutOrderNo(businessOrderId);
            model.setOutRequestNo(businessOrderId);
            model.setOrderTitle(subject);
            model.setAmount(orderAmount.toPlainString());
            model.setProductCode("PRE_AUTH");
            request.setBizModel(model);
            request.setNotifyUrl(BaseConst.ALIPAY_NOTICE_URL);

            AlipayFundAuthOrderFreezeResponse response = alipayClient.sdkExecute(request);
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
