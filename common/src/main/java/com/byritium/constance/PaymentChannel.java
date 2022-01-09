package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentChannel {//支付渠道
    ALI_PAY("ALI_PAY", "支付宝支付"),
    WECHAT_PAY("WECHAT_PAY", "微信支付"),
    APPLE_PAY("APPLE_PAY", "苹果支付"),
    PLATFORM_PAY("PLATFORM_PAY", "余额支付"),

    ;
    private final String channel;
    private final String message;

    PaymentChannel(String channel, String message) {
        this.channel = channel;
        this.message = message;
    }

    public String type() {
        return this.channel;
    }

    public static void verify(String type) {
        if (type == null) {
            throw new IllegalArgumentException("非法支付渠道");
        }
        for (PaymentChannel businessType : PaymentChannel.values()) {
            if (businessType.channel.equals(type)) {
                return;
            }
        }
        throw new IllegalArgumentException("非法支付渠道");
    }


}
