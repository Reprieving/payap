package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentState {
    PAYMENT_WAITING("PAYMENT_WAITING", "待处理"),
    PAYMENT_PENDING("PAYMENT_PENDING", "处理中"),
    PAYMENT_SUCCESS("PAYMENT_SUCCESS", "成功"),
    PAYMENT_FAIL("PAYMENT_FAIL", "失败"),
    PAYMENT_CLOSE("PAYMENT_CLOSE", "关闭"),
    ;

    private final String state;
    private final String message;

    PaymentState(String state, String message) {
        this.state = state;
        this.message = message;
    }
}
