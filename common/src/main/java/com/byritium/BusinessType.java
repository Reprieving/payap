package com.byritium;

import lombok.Getter;

@Getter
public enum BusinessType {

    FREE_WITHDRAW("FREE_WITHDRAW","提现冻结")
    ;
    private final String type;
    private final String message;

    BusinessType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
