package com.byritium.constance;

import lombok.Getter;

@Getter
public enum TransactionType {
    PAY("PAY", "支付"),
    PROFIT("PROFIT", "分账"),
    REFUND("REFUND", "退款"),
    RENEW("RENEW", "续费"),

    ;
    private String type;
    private String message;

    TransactionType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
