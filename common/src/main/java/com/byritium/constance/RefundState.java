package com.byritium.constance;

import lombok.Getter;

@Getter
public enum RefundState {
    TRANSACTION_NONE("TRANSACTION_NONE", "无退款"),
    TRANSACTION_PART("TRANSACTION_SUCCESS", "交易成功"),
    TRANSACTION_ALL("TRANSACTION_FAIL", "交易失败"),

    ;
    private final String state;
    private final String message;

    RefundState(String state, String message) {
        this.state = state;
        this.message = message;
    }
}
