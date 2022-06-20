package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.entity.payment.PaymentRechargeOrder;
import com.byritium.entity.payment.PaymentRefundOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import lombok.Data;

import java.util.Map;

@Data
public class TransactionResult {
    private Map<PaymentChannel, PaymentRechargeOrder> paymentOrders;
    private Map<PaymentChannel, PaymentRefundOrder> refundOrders;
    private TransactionTradeOrder tradeOrder;
    private PaymentState paymentState;
    private String transactionOrderId;
    private String paySign;
}
