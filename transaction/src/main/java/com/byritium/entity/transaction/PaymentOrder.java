package com.byritium.entity.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentChannelType;
import com.byritium.constance.PaymentState;
import com.byritium.constance.RefundState;
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
    private PaymentChannelType paymentChannelType;
    private Long paymentPatternId;
    private PaymentState paymentState = PaymentState.PAYMENT_WAITING;
    private RefundState refundState = RefundState.TRANSACTION_NONE;


    public PaymentOrder(TransactionTradeOrder transactionTradeOrder, PaymentChannelType paymentChannelType, Long mediumId, BigDecimal orderAmount) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUid(transactionTradeOrder.getUid());
        paymentOrder.setBizOrderId(transactionTradeOrder.getBizOrderId());
        paymentOrder.setTxOrderId(transactionTradeOrder.getId());
        paymentOrder.setMediumId(mediumId);
        paymentOrder.setPayerId(transactionTradeOrder.getPayerId());
        paymentOrder.setPayeeId(transactionTradeOrder.getPayeeId());
        paymentOrder.setSubject(transactionTradeOrder.getSubject());
        paymentOrder.setPaymentChannelType(paymentChannelType);
        paymentOrder.setOrderAmount(orderAmount);
    }

}
