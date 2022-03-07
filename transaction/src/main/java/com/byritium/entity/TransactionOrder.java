package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionOrder extends CommonEntity {
    private String id;
    private String clientId;
    private String accountCoreId;
    private String businessOrderId;
    private TransactionType transactionType;
    private String payerId;
    private String payeeId;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private TransactionState transactionState;
    private PaymentChannel paymentChannel;
    private PaymentState paymentState;
}
