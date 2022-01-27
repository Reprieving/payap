package com.byritium.constance;

import lombok.Getter;

@Getter
public enum TransactionState {
    TRANSACTION_PENDING("TRANSACTION_PENDING", "交易中"),
    TRANSACTION_SUCCESS("TRANSACTION_SUCCESS", "交易成功"),
    TRANSACTION_FAIL("TRANSACTION_FAIL", "交易失败"),
    TRANSACTION_CLOSE("TRANSACTION_CLOSE", "交易关闭"),

    ;
    private String state;
    private String message;

    TransactionState(String state, String message) {
        this.state = state;
        this.message = message;
    }
}