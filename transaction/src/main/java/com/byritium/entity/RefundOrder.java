package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.service.payment.type.Refund;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefundOrder extends CommonEntity implements Refund {
    private String id;
    private String bizId;
    private String payOrderId;
    private PaymentChannel paymentChannel;
    private String payerId;
    private String payeeId;
    private String payMediumId;
    private String paymentTitle;
    private BigDecimal orderAmount;
    private PaymentState state;
}
