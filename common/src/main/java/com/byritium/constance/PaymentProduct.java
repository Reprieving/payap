package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentProduct {
    QUICK_PAY("QUICK_PAY", "快捷支付"),
    CONTRACT_PAY("CONTRACT_PAY", "签约支付"),
    AUTH_PAY("AUTH_PAY", "授权支付"),

    ;
    private final String product;
    private final String message;

    PaymentProduct(String product, String message) {
        this.product = product;
        this.message = message;
    }
}
