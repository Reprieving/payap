package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionTransferOrder extends CommonEntity {
    private String id;
    private String clientId;
    private String businessOrderId;
    private String senderId;
    private String receiverIds;
    private BigDecimal orderAmount;
    private TransactionType transactionType;
    private TransactionState transactionState;
    private PaymentChannel paymentChannel;

    public TransactionTransferOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
    }

    public TransactionTransferOrder(String clientId, String businessOrderId, String senderId, String receiverIds, BigDecimal orderAmount, PaymentChannel paymentChannel) {
        this.clientId = clientId;
        this.businessOrderId = businessOrderId;
        this.receiverIds = receiverIds;
        this.orderAmount = orderAmount;
        this.transactionType = TransactionType.SETTLE;
        this.transactionState = TransactionState.TRANSACTION_PENDING;
        this.paymentChannel = paymentChannel;
    }
}
