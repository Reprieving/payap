package com.byritium.constance;

import lombok.Getter;

@Getter
public enum Os {//记录状态
    USABLE("USABLE", "可用"),
    DISABLE("DISABLE", "禁用"),
    DELETE("DELETE", "删除"),
    ;
    private String state;
    private String message;

    Os(String state, String message) {
        this.state = state;
        this.message = message;
    }

    public String type() {
        return this.state;
    }


}
