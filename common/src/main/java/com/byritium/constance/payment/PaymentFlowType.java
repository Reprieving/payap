package com.byritium.constance.payment;

import lombok.Getter;

@Getter
public enum PaymentFlowType {
    PAY("PAY", "支付"),
    REFUND("REFUND", "退款"),
    SETTLE("SETTLE", "结算"),
    WITHDRAW("WITHDRAW", "提现"),
    TRANSFER("TRANSFER", "转账"),
    ;
    private final String type;
    private final String message;

    PaymentFlowType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
