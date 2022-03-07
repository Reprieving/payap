package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.entity.TransactionPaymentOrder;
import lombok.Data;

import java.util.Map;

@Data
public class TransactionResult {
    private Map<PaymentChannel, TransactionPaymentOrder> result;
    private PaymentState paymentState;
    private String transactionOrderId;
}
