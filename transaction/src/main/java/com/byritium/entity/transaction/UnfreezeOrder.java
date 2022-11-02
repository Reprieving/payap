package com.byritium.entity.transaction;

import com.byritium.BusinessType;
import com.byritium.constance.TransactionState;
import com.byritium.entity.CommonEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UnfreezeOrder extends CommonEntity {
    private Long id;
    private Long clientId;
    private Long bizOrderId;
    private Long freezeOrderId;
    private Long uid;
    private BusinessType bizType;
    private BigDecimal freezeAmount;
    private TransactionState transactionState = TransactionState.TRANSACTION_PENDING;
}

