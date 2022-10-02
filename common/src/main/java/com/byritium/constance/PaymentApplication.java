package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentApplication {
    APP("APP", "APP"),
    WAP("WAP", "WAP"),
    PC("APP", "PC"),
    ;
    private final String application;
    private final String message;

    PaymentApplication(String application, String message) {
        this.application = application;
        this.message = message;
    }
}
