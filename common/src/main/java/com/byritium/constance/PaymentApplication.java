package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentApplication {
    APP("APP", "APP"),
    WAP("WAP", "WAP"),
    PC("APP", "PC"),
    CREATE_CODE("CREATE_CODE", "付款码支付"),
    SCAN_CODE("SCAN_CODE", "扫码支付"),
    ;
    private final String application;
    private final String message;

    PaymentApplication(String application, String message) {
        this.application = application;
        this.message = message;
    }
}
