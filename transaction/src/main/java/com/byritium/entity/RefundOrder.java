package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefundOrder extends CommonEntity {
    private String id;
    private String accountCoreId;
    private String businessOrderId;
    private String payOrderId;
    private PaymentChannel paymentChannel;
    private String payMediumId;
    private String paymentTitle;
    private BigDecimal orderAmount;
    private BigDecimal paymentAmount;
    private PaymentState state;
    private Boolean refundFlag;
    private String sign;
}
