package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentCurrency {//支付货币
    RMB("RMB", "人民币"),
    VIRTUAL_CURRENCY("VIRTUAL_CURRENCY", "虚拟币"),
    COUPON("COUPON", "优惠券"),



    ;
    private final String pattern;
    private final String message;

    PaymentCurrency(String pattern, String message) {
        this.pattern = pattern;
        this.message = message;
    }

    public String pattern() {
        return this.pattern;
    }


}
