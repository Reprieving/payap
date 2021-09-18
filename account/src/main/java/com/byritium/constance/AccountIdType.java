package com.byritium.constance;

import lombok.Getter;

@Getter
public enum AccountIdType {
    USER("USER", "用户类型"),
    ENTERPRISE("ENTERPRISE", "企业类型"),
    BUSINESS("BUSINESS", "业务线类型"),

    ;
    private String type;
    private String message;

    AccountIdType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
