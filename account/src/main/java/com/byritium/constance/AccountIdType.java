package com.byritium.constance;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
public enum AccountIdType {
    MEMBER("MEMBER", "会员"),
    ENTERPRISE("ENTERPRISE", "企业"),
    BUSINESS("BUSINESS", "业务线"),

    ;
    private final String type;
    private final String message;

    AccountIdType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
