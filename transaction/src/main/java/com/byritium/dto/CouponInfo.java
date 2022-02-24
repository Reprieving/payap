package com.byritium.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponInfo {
    private String payerId;
    private String couponId;
    private BigDecimal amount;
}
