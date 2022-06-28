package com.byritium.constance;

import lombok.Getter;

@Getter
public enum TransactionState {
    TRANSACTION_PENDING("TRANSACTION_PENDING", "交易中"),
    TRANSACTION_INTERRUPTING("TRANSACTION_INTERRUPTING", "交易终止"),
    TRANSACTION_SUCCESS("TRANSACTION_SUCCESS", "交易成功"),
    TRANSACTION_FAIL("TRANSACTION_FAIL", "交易失败"),
    TRANSACTION_CLOSE("TRANSACTION_CLOSE", "交易关闭"),

    ;
    private final String state;
    private final String message;

    TransactionState(String state, String message) {
        this.state = state;
        this.message = message;
    }
}
