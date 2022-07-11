package com.byritium.dto;

import com.byritium.constance.PaymentProtocol;
import lombok.Data;

import java.util.List;

@Data
public class PaymentParam {
    private String bizOrderId;
    private String txOrderId;
    private PaymentProtocol protocol;
    private List<PaymentOrder> orderList;
}
