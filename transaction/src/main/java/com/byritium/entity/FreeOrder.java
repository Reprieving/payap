package com.byritium.entity;

import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import com.byritium.service.payment.type.Transfer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class FreeOrder extends CommonEntity implements Transfer {
    private String id;
    private String clientId;
    private String bizOrderId;
    private String freezeOrderId;
    private String userId;
    private BigDecimal orderAmount;
    private TransactionType transactionType;
    private TransactionState transactionState;

    public FreeOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
    }

    public FreeOrder(String clientId, String bizOrderId, String userId, BigDecimal orderAmount) {
        this.clientId = clientId;
        this.bizOrderId = bizOrderId;
        this.userId = userId;
        this.orderAmount = orderAmount;
        this.transactionType = TransactionType.FREEZE;
        this.transactionState = TransactionState.TRANSACTION_PENDING;
    }
}
