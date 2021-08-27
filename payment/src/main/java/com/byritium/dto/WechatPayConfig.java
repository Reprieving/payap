package com.byritium.dto;

import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import lombok.Getter;

@Getter
@Setter
public class WechatPayConfig {
    private String appId;//AppId
    private String michId;//商户Id
    private String certificateSerialNo;//商户证书序列号
    private String certPath;//商户公钥
    private String privateKeyPath;//商户私钥
    private String p12Path;//商户证书
    private String apiKey;//apiKey（商户平台密钥）
    private String apiV3Key;//apiV3Key
    private String payUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/app";
    private String contractUrl = "https://api.mch.weixin.qq.com/pay/contractorder";
    private String refundUrl = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";
    private String withdrawUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    private String jsAppKey;
    private String jsAppSecret;

}
