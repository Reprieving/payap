package com.byritium.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RechargeParam {
    private Long bizOrderId;
    private Long uid;
    private Long rechargeId;
    private Long paymentSettingId;
    private BigDecimal rechargeAmount;
}
