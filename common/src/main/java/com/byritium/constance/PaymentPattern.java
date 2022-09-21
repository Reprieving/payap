package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentPattern {//支付模式
    ACCOUNT_PAY("ACCOUNT_PAY", "账户支付"),

    ALI_PAY("ALI_PAY", "支付宝支付"),
    ALI_PAY_APP("ALI_PAY_APP", "支付宝APP支付"),
    ALI_PAY_WAP("ALI_PAY_WAP", "支付手机网站支付"),
    ALI_PAY_PC("ALI_PAY_PC", "支付宝电脑网站支付"),
    ALI_PAY_SCAN_CODE("ALI_PAY_PC", "支付宝当面扫码支付"),
    ALI_PAY_CREATE_CODE("ALI_PAY_CREATE_CODE", "支付宝付款码支付"),
    ALI_PAY_AUTH_ONLINE("ALI_PAY_AUTH_ONLINE", "支付宝线上授权支付"),
    ALI_PAY_AUTH_OFFLINE_SCAN("ALI_PAY_AUTH_OFFLINE_SCAN", "支付宝线下授权支付(扫码)"),
    ALI_PAY_AUTH_OFFLINE_SHOW("ALI_PAY_AUTH_OFFLINE_SHOW", "支付宝线下授权支付(发码)"),

    WECHAT_PAY("WECHAT_PAY", "微信支付"),
    WECHAT_APP_PAY("WECHAT_APP_PAY", "微信APP支付"),

    APPLE_PAY("APPLE_PAY", "苹果支付"),


    ;
    private final String pattern;
    private final String message;

    PaymentPattern(String pattern, String message) {
        this.pattern = pattern;
        this.message = message;
    }

    public String pattern() {
        return this.pattern;
    }


}
