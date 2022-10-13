package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentType {
    ACCOUNT_PAY("ACCOUNT_PAY", "账户支付"),
    PAYMENT_AGENT("PAYMENT_AGENT", "支付机构"),
    DISCOUNT_PAY("DISCOUNT_PAY", "优惠活动支付"),
    COUPON_PAY("COUPON_PAY", "优惠券支付"),
    ;
    private final String type;
    private final String message;

    PaymentType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
