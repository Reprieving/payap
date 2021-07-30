package com.byritium.dto.wechat;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WechatPayAmount {
    private Integer total;//金额（分）
    private String currency = "CNY";//货币类型

    public WechatPayAmount(BigDecimal totalAmount) {
        this.total = totalAmount.multiply(BigDecimal.valueOf(100)).intValue();
    }
}
