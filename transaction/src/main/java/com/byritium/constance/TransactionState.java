package com.byritium.constance;

import lombok.Getter;

@Getter
public enum TransactionState {
    PENDING("PENDING", "交易中"),
    SUCCESS("SUCCESS", "交易成功"),
    FAIL("FAIL", "交易失败"),
    CLOSE("CLOSE", "交易已关闭"),

    ;
    private String state;
    private String message;

    TransactionState(String state, String message) {
        this.state = state;
        this.message = message;
    }
}
