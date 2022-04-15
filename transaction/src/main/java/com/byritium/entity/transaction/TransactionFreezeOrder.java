package com.byritium.entity.transaction;

import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import com.byritium.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionFreezeOrder extends CommonEntity  {
    private String id;
    private String clientId;
    private String bizOrderId;
    private String userId;
    private BigDecimal orderAmount;
    private TransactionType transactionType;
    private TransactionState transactionState;

    public TransactionFreezeOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
    }

    public TransactionFreezeOrder(String clientId, String bizOrderId, String userId, BigDecimal orderAmount) {
        this.clientId = clientId;
        this.bizOrderId = bizOrderId;
        this.userId = userId;
        this.orderAmount = orderAmount;
        this.transactionType = TransactionType.FREEZE;
        this.transactionState = TransactionState.TRANSACTION_PENDING;
    }
}
