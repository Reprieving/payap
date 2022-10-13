package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentChannel {
    BALANCE_PAY("BALANCE_PAY", "余额支付"),
    APPLE_PAY("APPLE_PAY", "苹果支付"),
    ALI_PAY("ALI_PAY", "支付宝"),
    WECHAT_PAY("WECHAT_PAY", "微信"),
    PAYPAL("PAYPAL", "PAYPAL"),
    UNION("UNION", "云闪付"),

    COUPON_PAY("COUPON_PAY", "优惠券支付"),
    DISCOUNT_PAY("DISCOUNT_PAY", "优惠活动支付"),
    VIRTUAL_CURRENCY_PAY("VIRTUAL_CURRENCY_PAY", "虚拟资产支付"),
    ;
    private final String channel;
    private final String message;

    PaymentChannel(String channel, String message) {
        this.channel = channel;
        this.message = message;
    }
}
