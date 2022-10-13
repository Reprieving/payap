package com.byritium.entity.transaction;

import com.byritium.constance.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentOrder {
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


    public PaymentOrder(TransactionTradeOrder transactionTradeOrder) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUid(transactionTradeOrder.getUid());
        paymentOrder.setBizOrderId(transactionTradeOrder.getBizOrderId());
        paymentOrder.setTxOrderId(transactionTradeOrder.getId());
        paymentOrder.setPayerId(transactionTradeOrder.getPayerId());
        paymentOrder.setPayeeId(transactionTradeOrder.getPayeeId());
        paymentOrder.setSubject(transactionTradeOrder.getSubject());
        paymentOrder.setOrderAmount(transactionTradeOrder.getOrderAmount());
        paymentOrder.setPaymentPatternId(transactionTradeOrder.getPaymentSettingId());
    }

    public PaymentOrder(TransactionTradeOrder transactionTradeOrder, PaymentType paymentType, Long mediumId, BigDecimal orderAmount) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUid(transactionTradeOrder.getUid());
        paymentOrder.setBizOrderId(transactionTradeOrder.getBizOrderId());
        paymentOrder.setTxOrderId(transactionTradeOrder.getId());
        paymentOrder.setMediumId(mediumId);
        paymentOrder.setPayerId(transactionTradeOrder.getPayerId());
        paymentOrder.setPayeeId(transactionTradeOrder.getPayeeId());
        paymentOrder.setSubject(transactionTradeOrder.getSubject());
        paymentOrder.setPaymentType(paymentType);
        paymentOrder.setOrderAmount(orderAmount);
    }

}
