package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionSettleOrder extends CommonEntity {
    private String id;
    private String clientId;
    private String transactionReceiptOrderId;
    private String receiverIds;
    private BigDecimal orderAmount;
    private TransactionType transactionType;
    private TransactionState transactionState;
    private PaymentChannel paymentChannel;
    private PaymentState paymentState;

    public TransactionSettleOrder() {
        this.transactionState = TransactionState.TRANSACTION_PENDING;
        this.paymentState = PaymentState.PAYMENT_PENDING;
    }

    public TransactionSettleOrder(String clientId, String transactionReceiptOrderId,String receiverIds,BigDecimal orderAmount,PaymentChannel paymentChannel) {
        this.clientId = clientId;
        this.transactionReceiptOrderId = transactionReceiptOrderId;
        this.receiverIds = receiverIds;
        this.orderAmount = orderAmount;
        this.transactionType = TransactionType.SETTLE;
        this.transactionState = TransactionState.TRANSACTION_PENDING;
        this.paymentChannel = paymentChannel;
        this.paymentState = PaymentState.PAYMENT_SUCCESS;
    }
}
