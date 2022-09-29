package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import com.byritium.service.callback.entity.PayOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PayParam {
    private long bizOrderId;
    private long txOrderId;
    private long uid;
    private long paymentPatternId;
    private String subject;
    private BigDecimal totalAmount;
}
