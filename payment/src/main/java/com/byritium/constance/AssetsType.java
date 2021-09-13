package com.byritium.constance;

import lombok.Getter;

@Getter
public enum AssetsType {
    BALANCE("BALANCE", "余额"),

    ;
    private String state;
    private String message;

    AssetsType(String state, String message) {
        this.state = state;
        this.message = message;
    }
}
