package com.byritium.entity;

import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnFreezeOrder extends CommonEntity {
    private String id;
    private String clientId;
    private String businessOrderId;
    private String userId;
    private BigDecimal orderAmount;
    private TransactionType transactionType;
    private TransactionState transactionState;

    public UnFreezeOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
    }

    public UnFreezeOrder(String clientId, String businessOrderId, String userId, BigDecimal orderAmount) {
        this.clientId = clientId;
        this.businessOrderId = businessOrderId;
        this.userId = userId;
        this.orderAmount = orderAmount;
        this.transactionType = TransactionType.FREEZE;
        this.transactionState = TransactionState.TRANSACTION_PENDING;
    }
}
