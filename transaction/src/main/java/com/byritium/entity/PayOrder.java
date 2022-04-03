package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayOrder extends CommonEntity {
    private String id;
    private String bizOrderId;
    private String transactionOrderId;
    private PaymentChannel paymentChannel;
    private String payerId;
    private String payeeId;
    private String payMediumId;
    private String paymentTitle;
    private BigDecimal orderAmount;
    private PaymentState state;
    private Boolean refundFlag;
}
