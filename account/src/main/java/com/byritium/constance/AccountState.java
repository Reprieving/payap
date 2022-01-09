package com.byritium.constance;

import lombok.Getter;

@Getter
public enum AccountState {
    NORMAL("NORMAL", "正常"),
    DESTROY("DESTROY", "注销"),
    FROZEN(" FROZEN", "冻结"),

    ;
    private final String state;
    private final String message;

    AccountState(String state, String message) {
        this.state = state;
        this.message = message;
    }
}
