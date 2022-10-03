package com.byritium.constance;

import lombok.Getter;

@Getter
public enum ClientType {
    ANDROID("ANDROID", "安卓"),
    IOS("IOS", "苹果"),
    WAP("WAP", "WAP"),
    WEB("WEB", "web"),
    ;
    private final String type;
    private final String message;

    ClientType(String state, String message) {
        this.type = state;
        this.message = message;
    }

    public String type() {
        return this.type;
    }


}
