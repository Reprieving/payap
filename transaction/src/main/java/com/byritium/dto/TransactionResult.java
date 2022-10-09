package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.PaymentState;
import com.byritium.entity.payment.PaymentRechargeOrder;
import com.byritium.entity.payment.PaymentRefundOrder;
import com.byritium.entity.transaction.TransactionOrder;
import lombok.Data;

import java.util.Map;

@Data
public class TransactionResult {
    private Map<PaymentPattern, PaymentRechargeOrder> paymentOrders;
    private Map<PaymentPattern, PaymentRefundOrder> refundOrders;
    private TransactionOrder tradeOrder;
    private PaymentState paymentState;
    private String transactionOrderId;
    private String paySign;
}
