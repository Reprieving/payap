package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionPayOrder extends CommonEntity {
    private String id;
    private String transactionReceiptOrderId;
    private PaymentChannel paymentChannel;
    private String payerId;
    private String payMediumId;
    private String paymentTitle;
    private BigDecimal orderAmount;
    private PaymentState state;
    private String sign;
}
