package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentScene {//支付场景
    ONLINE("ONLINE", "线上"),
    OFFLINE("OFFLINE", "线下"),


    ;
    private final String scene;
    private final String message;

    PaymentScene(String scene, String message) {
        this.scene = scene;
        this.message = message;
    }

    public String scene() {
        return this.scene;
    }


}
