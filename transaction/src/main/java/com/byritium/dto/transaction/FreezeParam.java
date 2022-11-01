package com.byritium.dto.transaction;

import com.byritium.BusinessType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreezeParam {
    private Long bizOrderId;
    private Long uid;
    private BusinessType bizType;
    private BigDecimal freezeAmount;
}
