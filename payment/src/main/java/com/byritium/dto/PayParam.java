package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import lombok.Data;

import java.util.List;

@Data
public class PayParam {
    private String bizOrderId;
    private String txOrderId;
    private String userId;
    private PaymentChannel paymentChannel;
    private List<PaymentOrder> orderList;
}
