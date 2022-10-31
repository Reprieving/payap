package com.byritium.entity.transaction;

import com.byritium.constance.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionPaymentOrder {
    private Long id;
    private Long uid;
    private Long bizOrderId;
    private Long txOrderId;
    private Long mediumId;
    private Long payerId;
    private Long payeeId;
    private String subject;
    private BigDecimal orderAmount;
    private PaymentType paymentType;
    private PaymentChannel paymentChannel;
    private Long paymentSettingId;
    private PaymentState paymentState = PaymentState.PAYMENT_WAITING;
    private RefundState refundState = RefundState.TRANSACTION_NONE;


    public TransactionPaymentOrder(TransactionTradeOrder transactionTradeOrder) {
        this.uid = transactionTradeOrder.getUid();
        this.bizOrderId = transactionTradeOrder.getBizOrderId();
        this.txOrderId = transactionTradeOrder.getId();
        this.payerId = transactionTradeOrder.getPayerId();
        this.payeeId = transactionTradeOrder.getPayeeId();
        this.subject = transactionTradeOrder.getSubject();
        this.orderAmount = transactionTradeOrder.getOrderAmount();
        this.paymentSettingId = transactionTradeOrder.getPaymentSettingId();
    }

    public TransactionPaymentOrder(TransactionTradeOrder transactionTradeOrder, PaymentType paymentType, Long mediumId, BigDecimal orderAmount) {
        this.uid = transactionTradeOrder.getUid();
        this.bizOrderId = transactionTradeOrder.getBizOrderId();
        this.mediumId = mediumId;
        this.payerId = transactionTradeOrder.getPayerId();
        this.payeeId = transactionTradeOrder.getPayeeId();
        this.subject = transactionTradeOrder.getSubject();
        this.paymentType = paymentType;
        this.orderAmount = orderAmount;
    }

}
