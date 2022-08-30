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
public class PaymentController {
    private final PayService payService;

    public PaymentController(PayService payService) {
        this.payService = payService;
    }


    @RequestMapping("pay")
    public void pay(@RequestBody PayParam param) {

    }

    @RequestMapping("pay/query")
    public void queryPay(@RequestBody PayParam param) {

    }

    @RequestMapping("refund")
    public void refund(@RequestBody PayParam param) {

    }

    @RequestMapping("transfer")
    public void transfer(@RequestBody PayParam param) {

    }

    @RequestMapping("withdraw")
    public void withdraw(@RequestBody PayParam param) {

    }
}
