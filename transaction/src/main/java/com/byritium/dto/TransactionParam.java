package com.byritium.dto;


import com.byritium.constance.PaymentPattern;
import com.byritium.constance.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionParam {
    private String clientId;
    private String businessOrderId;
    private String userId;
    private String payerId;
    private String payeeId;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private String title;
    private TransactionType transactionType;
    private PaymentPattern paymentPattern;
    private String couponId;
    private Deduction deduction;

    private String receiverIds;

    private String senderId;
    private String receiverId;
}
