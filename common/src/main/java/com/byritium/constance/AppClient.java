package com.byritium.constance;

import lombok.Getter;

@Getter
public enum AppClient {
    ANDROID("ANDROID", "安卓"),
    IOS("IOS", "苹果"),
    WEB("WEB", "web"),
    ;
    private final String type;
    private final String message;

    AppClient(String state, String message) {
        this.type = state;
        this.message = message;
    }

    public String type() {
        return this.type;
    }


}
