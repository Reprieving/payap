package com.byritium.constance.account;

import lombok.Getter;

@Getter
public enum AssetsType {
    RMB("RMB", "余额"),
    VIRTUAL_CURRENCY("VIRTUAL_CURRENCY", "虚拟资产"),
    ;
    private final String type;
    private final String message;

    AssetsType(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String type() {
        return this.type;
    }


}
