package com.byritium.entity.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionPayOrder extends CommonEntity {
    private String id;
    private String bizOrderId;
    private String transactionOrderId;
    private PaymentChannel paymentChannel;
    private String payerId;
    private String payeeId;
    private String payMediumId;
    private String paymentTitle;
    private BigDecimal orderAmount;
    private BigDecimal paymentAmount;
    private Boolean coreFlag;
}
