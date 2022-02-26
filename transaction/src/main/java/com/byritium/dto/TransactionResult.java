package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.entity.TransactionPayOrder;
import lombok.Data;

import java.util.Map;

@Data
public class TransactionResult {
    private Map<PaymentChannel, TransactionPayOrder> result;
    private PaymentState paymentState;
}
