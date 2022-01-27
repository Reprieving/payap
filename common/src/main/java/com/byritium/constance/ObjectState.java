package com.byritium.constance;

import lombok.Getter;

@Getter
public enum ObjectState {
    USABLE("USABLE", "可用"),
    DISABLE("DISABLE", "禁用"),
    DELETE("DELETE", "删除"),
    ;
    private final String state;
    private final String message;

    ObjectState(String state, String message) {
        this.state = state;
        this.message = message;
    }

    public String type() {
        return this.state;
    }


}
