package com.byritium.entity.transaction;

import com.byritium.constance.PaymentState;
import com.byritium.constance.RefundState;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentOrder {
    private Long id;
    private Long bizOrderId;
    private Long txOrderId;
    private Long uid;
    private Long mediumId;
    private Long payerId;
    private Long payeeId;
    private String subject;
    private BigDecimal orderAmount;
    private PaymentState paymentState = PaymentState.PAYMENT_WAITING;
    private RefundState refundState = RefundState.TRANSACTION_NONE;
}
