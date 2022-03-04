package com.byritium.entity;

import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionUnFreezeOrder extends CommonEntity {
    private String id;
    private String clientId;
    private String businessOrderId;
    private String userId;
    private BigDecimal orderAmount;
    private TransactionType transactionType;
    private TransactionState transactionState;

    public TransactionUnFreezeOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
    }

    public TransactionUnFreezeOrder(String clientId, String businessOrderId, String userId, BigDecimal orderAmount) {
        this.clientId = clientId;
        this.businessOrderId = businessOrderId;
        this.userId = userId;
        this.orderAmount = orderAmount;
        this.transactionType = TransactionType.FREEZE;
        this.transactionState = TransactionState.TRANSACTION_PENDING;
    }
}
