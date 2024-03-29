package com.byritium.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.byritium.constance.*;
import com.byritium.constance.alipay.AliPayCode;
import com.byritium.dto.alipay.AliPayConfig;
import com.byritium.dto.ClientInfo;
import com.byritium.dto.IdContainer;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.PaymentExtra;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.exception.BusinessException;
import com.byritium.service.PayService;
import com.byritium.service.QueryService;
import com.byritium.service.RefundService;
import com.byritium.service.WithdrawService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Slf4j
@Service
public class AliPayService implements PayService, RefundService, WithdrawService, QueryService {
    protected CertAlipayRequest buildRequest(AliPayConfig aliPayConfig) {
        //构造client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //设置网关地址
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        //设置应用Id
        certAlipayRequest.setAppId(aliPayConfig.getAppId());
        //设置应用私钥
        certAlipayRequest.setPrivateKey(aliPayConfig.getPrivateKey());
        //设置请求格式，固定值json
        certAlipayRequest.setFormat("json");
        //设置字符集
        certAlipayRequest.setCharset("UTF-8");
        //设置签名类型
        certAlipayRequest.setSignType("RSA2");
        //设置应用公钥证书路径
        certAlipayRequest.setCertPath(aliPayConfig.getAppCertPath());
        //设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(aliPayConfig.getAlipayCertPath());
        //设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(aliPayConfig.getAlipayRootCertPath());
        return certAlipayRequest;
    }

    @Override
    public PaymentChannel channel() {
        return PaymentChannel.ALI_PAY;
    }

    @Override
    public PaymentResult pay(ClientInfo clientInfo, PaymentSetting setting, IdContainer idContainer, String subject, BigDecimal orderAmount) {
        PaymentPattern pattern = PaymentPattern.pattern(setting);
        long businessOrderId = idContainer.getBizOrderId();
        AliPayConfig aliPayConfig = new AliPayConfig();
        CertAlipayRequest certAlipayRequest = buildRequest(aliPayConfig);
        AlipayClient alipayClient;
        switch (pattern) {
            case ONLINE_QUICK_ALIPAY_APP:
                try {
                    alipayClient = new DefaultAlipayClient(certAlipayRequest);
                    AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
                    AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
                    model.setSubject(subject);
                    model.setOutTradeNo(String.valueOf(businessOrderId));
                    model.setTimeExpire("30m");
                    model.setTotalAmount(orderAmount.toPlainString());
                    model.setProductCode("QUICK_MSECURITY_PAY");
                    request.setBizModel(model);
                    request.setNotifyUrl(BaseConst.ALIPAY_NOTICE_URL);
                    AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
                    String body = response.getBody();
                    Assert.state(!StringUtils.hasText(body), "支付宝签名失败");
                    PaymentResult paymentResult = new PaymentResult();
                    paymentResult.setPrePayId(body);
                    return paymentResult;
                } catch (AlipayApiException e) {
                    log.error("支付宝渠道支付失败，支付订单id：{}", businessOrderId, e);
                    throw new BusinessException("支付宝渠道支付失败");
                }

            case ONLINE_QUICK_ALIPAY_WAP:
                try {
                    alipayClient = new DefaultAlipayClient(certAlipayRequest);
                    AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
                    AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
                    model.setSubject(subject);
                    model.setOutTradeNo(String.valueOf(businessOrderId));
                    model.setTimeExpire("30m");
                    model.setTotalAmount(orderAmount.toPlainString());
                    request.setBizModel(model);
                    request.setNotifyUrl(BaseConst.ALIPAY_NOTICE_URL);

                    AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
                    String body = response.getBody();
                    Assert.state(!StringUtils.hasText(body), "支付宝签名失败");

                    PaymentResult paymentResult = new PaymentResult();
                    paymentResult.setPrePayId(body);

                    return paymentResult;
                } catch (AlipayApiException e) {
                    log.error("支付宝渠道支付失败，支付订单id：{}", businessOrderId, e);
                    throw new BusinessException("支付宝渠道支付失败");
                }

            case ONLINE_QUICK_ALIPAY_PC:
                try {
                    alipayClient = new DefaultAlipayClient(certAlipayRequest);
                    AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
                    AlipayTradePagePayModel model = new AlipayTradePagePayModel();
                    model.setSubject(subject);
                    model.setOutTradeNo(String.valueOf(businessOrderId));
                    model.setTimeExpire("30m");
                    model.setTotalAmount(orderAmount.toPlainString());
                    model.setProductCode("FAST_INSTANT_TRADE_PAY");
                    request.setBizModel(model);
                    request.setNotifyUrl(BaseConst.ALIPAY_NOTICE_URL);

                    AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
                    String body = response.getBody();
                    Assert.state(!StringUtils.hasText(body), "支付宝签名失败");

                    PaymentResult paymentResult = new PaymentResult();
                    paymentResult.setPrePayId(body);

                    return paymentResult;
                } catch (AlipayApiException e) {
                    log.error("支付宝渠道支付失败，支付订单id：{}", businessOrderId, e);
                    throw new BusinessException("支付宝渠道支付失败");
                }

            case OFFLINE_QUICK_ALIPAY_CREATE_CODE:
                try {
                    alipayClient = new DefaultAlipayClient(certAlipayRequest);
                    AlipayTradePayRequest request = new AlipayTradePayRequest();
                    AlipayTradePayModel model = new AlipayTradePayModel();
                    model.setSubject(subject);
                    model.setOutTradeNo(String.valueOf(businessOrderId));
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

            case OFFLINE_QUICK_ALIPAY_SCAN_CODE:
                try {
                    alipayClient = new DefaultAlipayClient(certAlipayRequest);
                    AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
                    AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
                    model.setSubject(subject);
                    model.setOutTradeNo(String.valueOf(businessOrderId));
                    model.setTotalAmount(orderAmount.toPlainString());
                    request.setBizModel(model);
                    request.setNotifyUrl(BaseConst.ALIPAY_NOTICE_URL);

                    AlipayTradePrecreateResponse response = alipayClient.execute(request);
                    String body = response.getBody();
                    Assert.state(!StringUtils.hasText(body), "支付宝签名失败");

                    PaymentResult paymentResult = new PaymentResult();
                    paymentResult.setPrePayId(body);

                    return paymentResult;
                } catch (AlipayApiException e) {
                    log.error("支付宝渠道支付失败，支付订单id：{}", businessOrderId, e);
                    throw new BusinessException("支付宝渠道支付失败");
                }

            case ONLINE_AUTH_ALIPAY_APP:
                try {
                    alipayClient = new DefaultAlipayClient(certAlipayRequest);
                    AlipayFundAuthOrderFreezeRequest request = new AlipayFundAuthOrderFreezeRequest();
                    AlipayFundAuthOrderFreezeModel model = new AlipayFundAuthOrderFreezeModel();
                    model.setAuthCode("");
                    model.setOutOrderNo(String.valueOf(businessOrderId));
                    model.setOutRequestNo(String.valueOf(businessOrderId));
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

            case OFFLINE_AUTH_ALIPAY_CREATE_CODE:
                try {
                    alipayClient = new DefaultAlipayClient(certAlipayRequest);
                    AlipayFundAuthOrderFreezeRequest request = new AlipayFundAuthOrderFreezeRequest();
                    AlipayFundAuthOrderFreezeModel model = new AlipayFundAuthOrderFreezeModel();
                    model.setAuthCode("");
                    model.setOutOrderNo(String.valueOf(businessOrderId));
                    model.setOutRequestNo(String.valueOf(businessOrderId));
                    model.setOrderTitle(subject);
                    model.setAmount(orderAmount.toPlainString());
                    model.setProductCode("PRE_AUTH_ONLINE");
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

            default:
                throw new BusinessException("error setting");
        }
    }

    @Override
    public void refund(String paymentOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra) {
        AliPayConfig aliPayConfig = new AliPayConfig();

        //构造request
        CertAlipayRequest certAlipayRequest = buildRequest(aliPayConfig);

        //构造client
        AlipayClient alipayClient;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setOutTradeNo(paymentOrderId);
            model.setTradeNo(refundOrderId);
            model.setRefundAmount(refundAmount.toPlainString());
            request.setBizModel(model);
            request.setNotifyUrl(BaseConst.ALIPAY_NOTICE_URL);

            AlipayTradeRefundResponse response = alipayClient.certificateExecute(request);
            String code = response.getCode();
            if (!response.isSuccess() || !AliPayCode.SUCCESS.code().equals(code)) {
                log.error(response.getSubMsg());
                throw new BusinessException(response.getSubMsg());
            }

        } catch (AlipayApiException e) {
            log.error("支付宝渠道退款失败，支付订单id：{}", paymentOrderId, e);
            throw new BusinessException("支付宝渠道退款失败");
        }
    }

    @Override
    public void withdraw(String businessOrderId, String userId, BigDecimal amount, PaymentExtra paymentExtra) {
        AliPayConfig aliPayConfig = new AliPayConfig();
        CertAlipayRequest certAlipayRequest = buildRequest(aliPayConfig);
        AlipayClient alipayClient;

        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
            AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
            AlipayFundTransAppPayModel model = new AlipayFundTransAppPayModel();
            model.setOutBizNo(businessOrderId);
            model.setTransAmount(amount.toPlainString());
            model.setProductCode("TRANS_ACCOUNT_NO_PWD");

            model.setBizScene("DIRECT_TRANSFER");
            model.setOrderTitle("资产提现");

            Participant payeeInfo = new Participant();
            payeeInfo.setIdentity(userId);
            payeeInfo.setIdentityType("ALIPAY_USER_ID");
            model.setPayeeInfo(payeeInfo);

            request.setBizModel(model);

            AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
            String rspJson = new Gson().toJson(response);

            if (response.isSuccess()) {
                log.info("支付宝渠道提现成功");
            } else {
                log.error("支付宝渠道提现失败,{}", rspJson);
                throw new BusinessException(response.getSubMsg());
            }

        } catch (AlipayApiException e) {
            log.error("支付宝渠道提现失败,订单id：{}", businessOrderId, e);
            throw new BusinessException("支付宝渠道提现失败");
        }
    }


    @Override
    public PaymentResult query(String businessOrderId, PaymentExtra paymentExtra) {
        return null;
    }


}
