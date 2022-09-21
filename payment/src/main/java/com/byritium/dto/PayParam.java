package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import com.byritium.service.callback.entity.PayOrder;
import lombok.Data;

import java.util.List;

@Data
public class PayParam {
    private String bizOrderId;
    private String txOrderId;
    private String userId;
    private PaymentPattern paymentPattern;
    private List<PayOrder> orderList;
}
