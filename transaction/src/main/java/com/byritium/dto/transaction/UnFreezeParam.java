package com.byritium.dto.transaction;

import com.byritium.BusinessType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UnFreezeParam {
    private Long bizOrderId;
    private Long uid;
    private Long freezeTxOrderId;
    private BusinessType bizType;
    private BigDecimal freezeAmount;
}
