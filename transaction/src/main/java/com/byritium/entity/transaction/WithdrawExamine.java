package com.byritium.entity.transaction;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.account.ExamineFlag;
import com.byritium.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class WithdrawExamine extends CommonEntity {
    private Long id;
    private Long withdrawOrderId;
    private Long examinerId;
    private ExamineFlag flag = ExamineFlag.PENDING;
    private String remark;
}
