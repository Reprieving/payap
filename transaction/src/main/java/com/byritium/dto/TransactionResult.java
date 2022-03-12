package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.entity.TransactiontOrder;
import lombok.Data;

import java.util.Map;

@Data
public class TransactionResult {
    private Map<PaymentChannel, TransactionPaymentOrder> paymentOrders;
    private TransactiontOrder transactiontOrder;
    private PaymentState paymentState;
    private String transactionOrderId;
}
