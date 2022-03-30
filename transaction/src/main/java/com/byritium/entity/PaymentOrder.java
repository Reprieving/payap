package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentOrder extends CommonEntity {
    private String id;
    private String businessOrderId;
    private String transactionOrderId;
    private PaymentChannel paymentChannel;
    private String payerId;
    private String payeeId;
    private String payMediumId;
    private String paymentTitle;
    private BigDecimal orderAmount;
    private BigDecimal paymentAmount;
    private PaymentState state;
    private Boolean refundFlag;
    private String sign;
}
