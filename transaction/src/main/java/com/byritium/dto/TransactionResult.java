package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.entity.PayOrder;
import com.byritium.entity.RefundOrder;
import com.byritium.entity.TransactionOrder;
import lombok.Data;

import java.util.Map;

@Data
public class TransactionResult {
    private Map<PaymentChannel, PayOrder> paymentOrders;
    private Map<PaymentChannel, RefundOrder> refundOrders;
    private TransactionOrder transactionOrder;
    private PaymentState paymentState;
    private String transactionOrderId;
}
