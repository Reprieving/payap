package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PAYMENT_PENDING("PAYMENT_PENDING", "支付中"),
    PAYMENT_SUCCESS("PAYMENT_SUCCESS", "支付成功"),
    PAYMENT_FAIL("PAYMENT_FAIL", "支付失败"),

    ;
    private final String state;
    private final String message;

    PaymentStatus(String state, String message) {
        this.state = state;
        this.message = message;
    }
}
