package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentProtocol {
    PAY("PAY", "收单支付"),
    REFUND("REFUND", "退款"),
    SETTLE("SETTLE", "结算"),
    ;
    private final String protocol;
    private final String message;

    PaymentProtocol(String protocol, String message) {
        this.protocol = protocol;
        this.message = message;
    }
}
