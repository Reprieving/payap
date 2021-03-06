package com.byritium.service.impl;

import com.byritium.constance.BaseConst;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;
import com.byritium.dto.WechatPayConfig;
import com.byritium.dto.wechat.WechatPayAmount;
import com.byritium.dto.wechat.WechatPayRequest;
import com.byritium.dto.wechat.WechatPayResult;
import com.byritium.exception.BusinessException;
import com.byritium.service.IPayService;
import com.byritium.utils.OkHttpUtils;
import com.byritium.utils.RandomUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

@Slf4j
@Service
public class WechatPayAppService extends WechatPayService implements IPayService {

    @Override
    public PaymentResult pay(String businessOrderId, String subject, BigDecimal orderAmount, PaymentExtra paymentExtra) {
        WechatPayConfig wechatPayConfig = new WechatPayConfig();

        String appId = wechatPayConfig.getAppId();
        String michId = wechatPayConfig.getMichId();
        String url = wechatPayConfig.getPayUrl();
        String nonceStr = RandomUtils.uuid();
        String certificateSerialNo = wechatPayConfig.getCertificateSerialNo();
        String privateKeyPath = wechatPayConfig.getPrivateKeyPath();


        WechatPayRequest wechatPayRequest = new WechatPayRequest();
        wechatPayRequest.setAppid(appId);
        wechatPayRequest.setMchid(michId);
        wechatPayRequest.setDescription(subject);
        wechatPayRequest.setOut_trade_no(businessOrderId);
        wechatPayRequest.setAmount(new WechatPayAmount(orderAmount));
        wechatPayRequest.setNotify_url(BaseConst.WECHATPAY_NOTICE_URL);

        Gson gson = new Gson();
        String json = gson.toJson(wechatPayRequest);

        String str;
        try {
            Map<String, String> map = buildHeader(HttpMethod.POST.toString(), url, json, nonceStr, michId, certificateSerialNo, privateKeyPath);
            str = OkHttpUtils.httpPostJson(url, map, json);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | IOException e) {
            throw new BusinessException("??????????????????????????????");
        }

        WechatPayResult wechatPayResult = gson.fromJson(str, WechatPayResult.class);
        if (!StringUtils.hasText(wechatPayResult.getCode())) {
            log.error("??????????????????,{}", str);
            throw new BusinessException("????????????");
        }

        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setPrePayId(wechatPayResult.getPrepay_id());
        return null;
    }

    @Override
    public void refund(String businessOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra) {
        super.refund(businessOrderId, refundOrderId, orderAmount, refundAmount, paymentExtra);
    }

    @Override
    public PaymentResult query(String businessOrderId, PaymentExtra paymentExtra) {
        return super.query(businessOrderId, paymentExtra);
    }
}
