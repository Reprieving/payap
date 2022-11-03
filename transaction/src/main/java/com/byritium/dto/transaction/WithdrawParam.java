package com.byritium.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawParam {
    private Long clientId;
    private Long bizOrderId;
    private Long uid;
    private Long freezeTxOrderId;
    private Long paymentSettingId;
    private BigDecimal withdrawAmount;
}
