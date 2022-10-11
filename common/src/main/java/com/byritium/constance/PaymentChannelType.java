package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentChannelType {
    ACCOUNT_PAY("ACCOUNT_PAY", "账户支付"),
    PAYMENT_AGENT("PAYMENT_AGENT", "支付机构"),
    MARKETING("MARKETING", "营销中心"),
    ;
    private final String type;
    private final String message;

    PaymentChannelType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
