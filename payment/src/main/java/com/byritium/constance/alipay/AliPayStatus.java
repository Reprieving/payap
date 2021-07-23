package com.byritium.constance.alipay;

import lombok.Getter;

@Getter
public enum AliPayStatus {//结算单类型
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "交易待付款"),
    TRADE_CLOSED("TRADE_CLOSED", "交易关闭"),
    TRADE_SUCCESS("TRADE_SUCCESS", "支付成功"),
    TRADE_FINISHED("TRADE_FINISHED", "交易结束"),


    ;
    private String status;
    private String message;

    AliPayStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String status() {
        return this.status;
    }
}
