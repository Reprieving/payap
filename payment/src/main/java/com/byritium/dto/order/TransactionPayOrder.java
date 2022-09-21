package com.byritium.dto.order;

import com.byritium.constance.PaymentPattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionPayOrder {
    private String id;
    private String bizOrderId;
    private String transactionOrderId;
    private PaymentPattern paymentPattern;
    private String payerId;
    private String payeeId;
    private String payMediumId;
    private String paymentTitle;
    private BigDecimal orderAmount;
    private BigDecimal paymentAmount;
    private Boolean coreFlag;
}
