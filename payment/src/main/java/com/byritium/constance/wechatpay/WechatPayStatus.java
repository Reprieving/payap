package com.byritium.constance.wechatpay;

import lombok.Getter;

@Getter
public enum WechatPayStatus {//微信支付状态
    SUCCESS("SUCCESS", "支付成功"),
    ;
    private String status;
    private String message;

    WechatPayStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String status() {
        return this.status;
    }
}
