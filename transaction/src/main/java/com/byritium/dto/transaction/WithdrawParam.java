package com.byritium.dto.transaction;

import com.byritium.constance.account.ExamineFlag;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawParam {
    private Long clientId;
    private Long bizOrderId;
    private Long uid;
    private Long withdrawOrderId;
    private Long paymentSettingId;
    private BigDecimal withdrawAmount;

    private Long examinerId;
    private ExamineFlag flag;
    private String remark;
}
