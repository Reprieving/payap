package com.byritium.constance;

import lombok.Getter;

@Getter
public enum AccountType {
    CASH("CASH", "现金"),

    ;
    private final String type;
    private final String message;

    AccountType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
