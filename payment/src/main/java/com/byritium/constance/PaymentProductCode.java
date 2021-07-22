package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentProductCode {
    PAY("PAY", "支付"),
    SETTLE("SETTLE", "结算"),
    REFUND("REFUND", "退款"),
    WITHDRAW("WITHDRAW", "提现"),
    ;
    private String code;
    private String message;

    PaymentProductCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
