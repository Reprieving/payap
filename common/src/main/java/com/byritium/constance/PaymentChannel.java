package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentChannel {
    APPLE_PAY("APPLE_PAY", "苹果支付"),
    ALI_PAY("ALI_PAY", "支付宝"),
    WECHAT_PAY("WECHAT_PAY", "微信"),
    PAYPAL("PAYPAL", "PAYPAL"),
    UNION("UNION", "云闪付"),
    ;
    private final String channel;
    private final String message;

    PaymentChannel(String channel, String message) {
        this.channel = channel;
        this.message = message;
    }
}
