package com.byritium.dto.recharge;

import com.byritium.constance.ClientType;
import com.byritium.constance.account.AssetsType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RechargeProduct {
    private ClientType clientType;
    private AssetsType assetsType;
    private BigDecimal value;
    private BigDecimal num;
}
