package com.byritium.service.impl;


import com.byritium.constance.BaseConst;
import com.byritium.constance.InterfaceProvider;
import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentPattern;
import com.byritium.dto.*;
import com.byritium.dto.wechat.*;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.exception.BusinessException;
import com.byritium.service.PayService;
import com.byritium.service.QueryService;
import com.byritium.service.RefundService;
import com.byritium.service.WithdrawService;
import com.byritium.utils.MD5Util;
import com.byritium.utils.OkHttpUtils;
import com.byritium.utils.RandomUtils;
import com.byritium.utils.XmlUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

@Slf4j
@Service
public class WechatPayService extends WechatCommonService implements PayService, RefundService, WithdrawService, QueryService {

    @Override
    public PaymentChannel channel() {
        return null;
    }

    @Override
    public PaymentResult pay(ClientInfo clientInfo, PaymentSetting setting, IdContainer idContainer, String subject, BigDecimal orderAmount) throws FileNotFoundException {
        WechatPayConfig wechatPayConfig = new WechatPayConfig();

        String appId = wechatPayConfig.getAppId();
        String michId = wechatPayConfig.getMichId();
        String url = wechatPayConfig.getPayUrl();
        String nonceStr = RandomUtils.uuid();

        long businessOrderId = idContainer.getBizOrderId();
        long transactionOrderId = idContainer.getTxOrderId();
        PaymentPattern pattern = PaymentPattern.pattern(setting);

        WechatPayRequest wechatPayRequest = new WechatPayRequest();
        wechatPayRequest.setAppid(appId);
        wechatPayRequest.setMchid(michId);
        wechatPayRequest.setDescription(subject);
        wechatPayRequest.setOutTradeNo(String.valueOf(businessOrderId));
        wechatPayRequest.setAmount(new WechatPayAmount(orderAmount));
        wechatPayRequest.setNotifyUrl(BaseConst.WECHATPAY_NOTICE_URL);


        switch (pattern) {
            case ONLINE_QUICK_WECHATPAY_APP:
                url = "https://api.mch.weixin.qq.com/v3/pay/transactions/app";
                break;

            case ONLINE_QUICK_WECHATPAY_WAP:
                url = "https://api.mch.weixin.qq.com/v3/pay/transactions/h5";
                break;

            case ONLINE_QUICK_WECHATPAY_PC:
                url = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
                break;

            case OFFLINE_QUICK_WECHATPAY_CREATE_CODE:
                url = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";
                break;

        }

        Gson gson = new Gson();
        String json = gson.toJson(wechatPayRequest);

        String str;
        try {
            Map<String, String> map = buildHeader(HttpMethod.POST.toString(), url, json,nonceStr);
            str = OkHttpUtils.httpPostJson(url, map, json);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | IOException e) {
            throw new BusinessException("微信支付渠道支付失败");
        }

        WechatPayResult wechatPayResult = gson.fromJson(str, WechatPayResult.class);
        if (!StringUtils.hasText(wechatPayResult.getCode())) {
            log.error("微信支付失败,{}", str);
            throw new BusinessException("支付失败");
        }

        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setPrePayId(wechatPayResult.getPrepay_id());
        return paymentResult;
    }


    @Override
    public void refund(String paymentOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra) {
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        String url = wechatPayConfig.getRefundUrl();

        String nonceStr = RandomUtils.uuid();

        WechatRefundRequest wechatRefundRequest = new WechatRefundRequest();
        wechatRefundRequest.setOut_trade_no(paymentOrderId);
        wechatRefundRequest.setOut_refund_no(refundOrderId);
        wechatRefundRequest.setNotify_url(BaseConst.WECHATPAY_NOTICE_URL);
        wechatRefundRequest.setAmount(new WechatRefundAmount(orderAmount, refundAmount));

        Gson gson = new Gson();
        String json = gson.toJson(wechatRefundRequest);
        String str;

        try {
            Map<String, String> map = buildHeader(HttpMethod.POST.toString(), url, json,nonceStr);
            str = OkHttpUtils.httpPostJson(url, map, json);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | IOException e) {
            log.error("微信支付构建请求头失败:{}", json);
            throw new BusinessException("微信支付渠道退款失败");
        }

        WechatPayResult wechatPayResult = gson.fromJson(str, WechatPayResult.class);
        if (!StringUtils.hasText(wechatPayResult.getCode())) {
            log.error("微信支付退款失败，参数：{}", str);
            throw new BusinessException("支付失败");
        }
    }

    @Override
    public void withdraw(String businessOrderId, String sdkId, BigDecimal amount, PaymentExtra paymentExtra) {
        WechatPayConfig wechatPayConfig = new WechatPayConfig();

        String url = wechatPayConfig.getWithdrawUrl();
        String appId = wechatPayConfig.getAppId();
        String apiKey = wechatPayConfig.getApiKey();
        String michId = wechatPayConfig.getMichId();
        String nonceStr = RandomUtils.uuid();

        String p12Path = wechatPayConfig.getP12Path();

        WechatWithdrawRequest wechatWithdrawRequest = new WechatWithdrawRequest();
        wechatWithdrawRequest.setMch_appid(appId);
        wechatWithdrawRequest.setMchid(michId);
        wechatWithdrawRequest.setNonce_str(nonceStr);
        wechatWithdrawRequest.setPartner_trade_no(businessOrderId);
        wechatWithdrawRequest.setOpenid(sdkId);
        wechatWithdrawRequest.setCheck_name("NO_CHECK");
        wechatWithdrawRequest.setAmount(amount.multiply(BigDecimal.valueOf(100)).longValue());
        wechatWithdrawRequest.setDesc("提现");

        Gson gson = new Gson();

        String json = gson.toJson(wechatWithdrawRequest);

        String str;

        Map<String, String> map;
        Map<String, String> param;

        try {

            map = buildHeader(HttpMethod.POST.toString(), url, json,nonceStr);

            param = gson.fromJson(json, TypeToken.getParameterized(Map.class, String.class, String.class).getType());
            String sign = buildWechatSign(param, apiKey);
            param.put("sign", sign);
            String xml = XmlUtils.mapToXml(param, false);

            SSLRequest sslRequest = new SSLRequest(InterfaceProvider.WECHAT_PAY, p12Path, michId);
            str = OkHttpUtils.httpPostXml(url, map, xml, sslRequest);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | IOException e) {
            log.error("微信支付构建请求头失败:{}", json);
            throw new BusinessException("微信支付渠道提现失败");
        }

        Map<String, String> resultMap = XmlUtils.xmlToMap(str);
        log.info("微信支付提现结果：{}", gson.toJson(resultMap));
        String resultCode = resultMap.get("result_code");
        if (!"SUCCESS".equals(resultCode)) {
            log.error("微信支付提现失败，参数：{}", gson.toJson(resultMap));
            throw new BusinessException(resultMap.get("return_msg"));
        }
    }

    @Override
    public PaymentResult query(String businessOrderId, PaymentExtra paymentExtra) {
        return null;
    }
}
