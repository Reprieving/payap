package com.byritium.entity.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionRefundOrder extends CommonEntity {
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
