package com.byritium.constance;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
public enum AccountIdType {
    USER("USER", "用户"),
    ENTERPRISE("ENTERPRISE", "企业"),
    BUSINESS("BUSINESS", "业务线"),

    ;
    private String type;
    private String message;

    AccountIdType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
