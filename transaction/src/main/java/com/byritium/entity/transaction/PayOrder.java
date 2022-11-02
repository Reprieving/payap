package com.byritium.entity.transaction;

import com.byritium.constance.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PayOrder {
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


    public PayOrder(TradeOrder tradeOrder) {
        this.uid = tradeOrder.getUid();
        this.bizOrderId = tradeOrder.getBizOrderId();
        this.txOrderId = tradeOrder.getId();
        this.payerId = tradeOrder.getPayerId();
        this.payeeId = tradeOrder.getPayeeId();
        this.subject = tradeOrder.getSubject();
        this.orderAmount = tradeOrder.getOrderAmount();
        this.paymentSettingId = tradeOrder.getPaymentSettingId();
    }

    public PayOrder(TradeOrder tradeOrder, PaymentType paymentType, Long mediumId, BigDecimal orderAmount) {
        this.uid = tradeOrder.getUid();
        this.bizOrderId = tradeOrder.getBizOrderId();
        this.mediumId = mediumId;
        this.payerId = tradeOrder.getPayerId();
        this.payeeId = tradeOrder.getPayeeId();
        this.subject = tradeOrder.getSubject();
        this.paymentType = paymentType;
        this.orderAmount = orderAmount;
    }

}
