package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.PaymentState;
import com.byritium.dto.payment.PrepayParam;
import lombok.Data;

import java.util.Map;

@Data
public class TransactionResult {
    private Long bizOrderId;
    private Long txOrderId;
    private PrepayParam prepayParam;
    private PaymentState paymentState;

}
