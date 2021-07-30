package com.byritium.constance;

import lombok.Getter;

@Getter
public enum InterfaceProvider {
    WECHAT_PAY("WECHAT_PAY", "微信支付"),


    ;
    private String provider;
    private String message;

    InterfaceProvider(String provider, String message) {
        this.provider = provider;
        this.message = message;
    }

    public String channel() {
        return this.provider;
    }

}
