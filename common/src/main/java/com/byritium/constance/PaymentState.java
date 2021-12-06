package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentState {
    PAYMENT_PENDING("PAYMENT_PENDING", "支付中"),
    PAYMENT_SUCCESS("PAYMENT_SUCCESS", "支付成功"),
    PAYMENT_FAIL("PAYMENT_FAIL", "支付失败"),

    ;
    private String state;
    private String message;

    PaymentState(String state, String message) {
        this.state = state;
        this.message = message;
    }
}
