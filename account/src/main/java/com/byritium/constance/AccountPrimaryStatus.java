package com.byritium.constance;

import lombok.Getter;

@Getter
public enum AccountPrimaryStatus {
    NORMAL("NORMAL", "正常"),
    DESTROY("DESTROY", "注销"),
    FROZEN("FROZEN", "冻结"),

    ;
    private String status;
    private String message;

    AccountPrimaryStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
