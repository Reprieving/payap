package com.byritium.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransAppPayModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.byritium.constance.BaseConst;
import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.constance.alipay.AliPayCode;
import com.byritium.dto.AliPayConfig;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
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

import javax.management.Query;
import java.math.BigDecimal;

@Slf4j
public abstract class AliPayService implements PayService, RefundService, WithdrawService, QueryService {
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
    public PaymentProduct product() {
        return null;
    }

    @Override
    public PaymentChannel channel() {
        return null;
    }

    @Override
    public abstract PayParam pay(String businessOrderId, String subject, BigDecimal orderAmount, PaymentExtra paymentExtra);

    @Override
    public void refund(String businessOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra) {
        AliPayConfig aliPayConfig = new AliPayConfig();

        //构造request
        CertAlipayRequest certAlipayRequest = buildRequest(aliPayConfig);

        //构造client
        AlipayClient alipayClient;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setOutTradeNo(businessOrderId);
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
            log.error("支付宝渠道退款失败，支付订单id：{}", businessOrderId, e);
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
            model.setOrderTitle("仟伴达人APP提现");

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
    public PayParam query(String businessOrderId, PaymentExtra paymentExtra) {
        return null;
    }
}
