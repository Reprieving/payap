package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentChannel {//支付渠道
    ACCOUNT_PAY("ACCOUNT_PAY", "账户支付"),

    ALI_PAY("ALI_PAY", "支付宝支付"),
    ALI_APP_PAY("ALI_APP_PAY", "支付宝APP支付"),

    WECHAT_PAY("WECHAT_PAY", "微信支付"),
    WECHAT_APP_PAY("WECHAT_APP_PAY", "微信APP支付"),

    APPLE_PAY("APPLE_PAY", "苹果支付"),


    ;
    private String channel;
    private String message;

    PaymentChannel(String channel, String message) {
        this.channel = channel;
        this.message = message;
    }

    public String channel() {
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
