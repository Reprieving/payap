package com.byritium.dto.transaction;

import com.byritium.constance.account.ExamineFlag;
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

    private Long examineId;
    private ExamineFlag flag;
}
