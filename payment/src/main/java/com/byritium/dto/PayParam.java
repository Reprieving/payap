package com.byritium.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayParam {
    private String michId;
    private String prePayId;
    private String nonceStr;
    private String timestamp;
    private String sign;
    private BigDecimal amount;
}
