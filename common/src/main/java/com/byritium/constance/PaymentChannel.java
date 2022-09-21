package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentChannel {
    ACCOUNT_PAY("ACCOUNT_PAY", "账户"),
    ALI_PAY("ALI_PAY", "支付宝"),
    WECHAT_PAY("WECHAT_PAY", "微信"),

    ;
    private final String channel;
    private final String message;

    PaymentChannel(String channel, String message) {
        this.channel = channel;
        this.message = message;
    }
}
