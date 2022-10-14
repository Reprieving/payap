package com.byritium.service;

import com.byritium.rpc.PaymentRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentExecutor {

    @Autowired
    private PaymentRpc paymentRpc;


}
