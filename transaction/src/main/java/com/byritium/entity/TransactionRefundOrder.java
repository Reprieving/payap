package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRefundOrder extends CommonEntity {
    private String id;
    private String transactionPayOrderId;
    private PaymentChannel paymentChannel;
    private String payerId;
    private String payMediumId;
    private BigDecimal orderAmount;
    private PaymentState state;

    public TransactionRefundOrder(String payerId, String transactionPayOrderId, PaymentChannel paymentChannel, String payMediumId, BigDecimal orderAmount) {
        this.payerId = payerId;
        this.transactionPayOrderId = transactionPayOrderId;
        this.paymentChannel = paymentChannel;
        this.payMediumId = payMediumId;
        this.orderAmount = orderAmount;
        this.state = PaymentState.PAYMENT_PENDING;
        super.init();
    }

    public TransactionRefundOrder(String payerId, String transactionPayOrderId, PaymentChannel paymentChannel, BigDecimal orderAmount) {
        this.payerId = payerId;
        this.transactionPayOrderId = transactionPayOrderId;
        this.paymentChannel = paymentChannel;
        this.orderAmount = orderAmount;
        this.state = PaymentState.PAYMENT_PENDING;
        super.init();
    }
}
