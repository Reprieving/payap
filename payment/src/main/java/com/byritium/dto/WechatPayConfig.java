package com.byritium.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class WechatPayConfig {
    private String appId;//AppId
    private String michId;//商户Id
    private String certificateSerialNo;//商户证书序列号
    private String certPath;//商户公钥
    private String privateKeyPath;//商户私钥
    private String p12Path;//商户证书
    private String apiKey;//apiKey（商户平台密钥）
    private String apiV3Key;//apiV3Key
    private String host = "https://api.mch.weixin.qq.com";
    private String payPath = "/v3/pay/transactions/app";
    private String contractPath = "/pay/contractorder";
    private String refundPath = "/v3/refund/domestic/refunds";
    private String withdrawPath = "/mmpaymkttransfers/promotion/transfers";
    private String jsAppKey;
    private String jsAppSecret;

}
