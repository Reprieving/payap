package com.byritium.constance;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(0, "success"),
    ERROR(-1, "exception"),
    ;
    private final Integer code;
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
