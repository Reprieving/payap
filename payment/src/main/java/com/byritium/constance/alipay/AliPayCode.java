package com.byritium.constance.alipay;

import lombok.Getter;

@Getter
public enum AliPayCode {//支付宝下单接口码
    SUCCESS("10000", "交易成功"),


    ;
    private String code;
    private String message;

    AliPayCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return this.code;
    }
}
