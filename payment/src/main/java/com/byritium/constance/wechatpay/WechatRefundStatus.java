package com.byritium.constance.wechatpay;

import lombok.Getter;

@Getter
public enum WechatRefundStatus {//微信退款状态
    SUCCESS("REFUND.SUCCESS", "退款成功"),
    FAILURE("REFUND.ABNORMAL", "退款异常"),
    CLOSED("REFUND.CLOSED", "退款已关闭"),

    ;
    private String status;
    private String message;

    WechatRefundStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String status() {
        return this.status;
    }
}
