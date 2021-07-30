package com.byritium.dto.wechat;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WechatRefundAmount {
    private Integer total;//总金额（分）
    private Integer refund;//退款金额（分）
    private Integer payer_total;//用户支付金额（分）
    private Integer payer_refund;//退款退款金额（分）
    private String currency = "CNY";//货币类型

    public WechatRefundAmount(BigDecimal totalAmount, BigDecimal refundAmount) {
        BigDecimal percent = BigDecimal.valueOf(100);
        this.total = totalAmount.multiply(percent).intValue();
        this.refund = refundAmount.multiply(percent).intValue();
    }
}
