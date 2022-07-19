package com.byritium.controller;


import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PayParam;
import com.byritium.entity.PayOrder;
import com.byritium.service.payment.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pay")
public class PayController {
    final PayService payService;

    public PayController(PayService payService) {
        this.payService = payService;
    }

    @RequestMapping("")
    public void pay(@RequestBody PayParam param) {
        PaymentChannel paymentChannel = param.getPaymentChannel();
        List<PayOrder> orderList = param.getOrderList();

    }


}
