package com.byritium.constance.payment;

import lombok.Getter;

@Getter
public enum PrepayParamType {
    SIGN("SIGN", "签名"),
    CODE_URL("CODE_URL", "二维码链接"),
    H5_URL("H5_URL", "H5链接"),
    ;
    private final String type;
    private final String message;

    PrepayParamType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
