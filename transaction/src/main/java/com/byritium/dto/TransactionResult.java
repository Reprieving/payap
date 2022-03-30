package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.entity.PaymentOrder;
import com.byritium.entity.TransactionOrder;
import lombok.Data;

import java.util.Map;

@Data
public class TransactionResult {
    private Map<PaymentChannel, PaymentOrder> paymentOrders;
    private TransactionOrder transactionOrder;
    private PaymentState paymentState;
    private String transactionOrderId;
}
