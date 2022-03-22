package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentType {
    PAY("PAY", "支付"),
    REFUND("REFUND", "退款"),
    SETTLE("SETTLE", "结算"),
    ;
    private final String type;
    private final String message;

    PaymentType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
