package com.byritium.service.callback.entity;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class PaymentOrder {


    private String id;


    private String clientId;


    private String businessOrderId;


    private String transactionOrderId;


    private String payerId;


    private String payeeId;


    private String subject;


    private BigDecimal orderAmount;


    private BigDecimal payAmount;


    private PaymentPattern paymentPattern;


    private PaymentStatus paymentStatus;


    private Timestamp createTime;

}
