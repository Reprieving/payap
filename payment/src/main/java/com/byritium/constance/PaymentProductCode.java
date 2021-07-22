package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentProductCode {
    ORDER_PAY("ORDER_PAY", "收单支付"),
    ORDER_SETTLE("ORDER_SETTLE", "订单结算"),
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
