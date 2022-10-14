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
    private Long paymentPatternId;
    private PaymentState paymentState = PaymentState.PAYMENT_WAITING;
    private RefundState refundState = RefundState.TRANSACTION_NONE;


    public TransactionPaymentOrder(TransactionTradeOrder transactionTradeOrder) {
        TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder();
        transactionPaymentOrder.setUid(transactionTradeOrder.getUid());
        transactionPaymentOrder.setBizOrderId(transactionTradeOrder.getBizOrderId());
        transactionPaymentOrder.setTxOrderId(transactionTradeOrder.getId());
        transactionPaymentOrder.setPayerId(transactionTradeOrder.getPayerId());
        transactionPaymentOrder.setPayeeId(transactionTradeOrder.getPayeeId());
        transactionPaymentOrder.setSubject(transactionTradeOrder.getSubject());
        transactionPaymentOrder.setOrderAmount(transactionTradeOrder.getOrderAmount());
        transactionPaymentOrder.setPaymentPatternId(transactionTradeOrder.getPaymentSettingId());
    }

    public TransactionPaymentOrder(TransactionTradeOrder transactionTradeOrder, PaymentType paymentType, Long mediumId, BigDecimal orderAmount) {
        TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder();
        transactionPaymentOrder.setUid(transactionTradeOrder.getUid());
        transactionPaymentOrder.setBizOrderId(transactionTradeOrder.getBizOrderId());
        transactionPaymentOrder.setTxOrderId(transactionTradeOrder.getId());
        transactionPaymentOrder.setMediumId(mediumId);
        transactionPaymentOrder.setPayerId(transactionTradeOrder.getPayerId());
        transactionPaymentOrder.setPayeeId(transactionTradeOrder.getPayeeId());
        transactionPaymentOrder.setSubject(transactionTradeOrder.getSubject());
        transactionPaymentOrder.setPaymentType(paymentType);
        transactionPaymentOrder.setOrderAmount(orderAmount);
    }

}
