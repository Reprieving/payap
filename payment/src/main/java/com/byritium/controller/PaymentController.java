package com.byritium.controller;


import com.byritium.dto.PayParam;
import com.byritium.service.IPayService;
import com.byritium.service.payment.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final PayService payService;

    public PaymentController(PayService payService) {
        this.payService = payService;
    }

    @RequestMapping("pay")
    public void pay(@RequestBody PayParam param) {
        payService.call(param.getPaymentChannel(), param.getOrderList());
    }

    @RequestMapping("withdraw")
    public void withdraw() {

    }

    @RequestMapping("transfer")
    public void transfer() {

    }

    @RequestMapping("refund")
    public void refund() {

    }

    @RequestMapping("free")
    public void free() {

    }

    @RequestMapping("unfree")
    public void unfree() {

    }

    //入款
    @RequestMapping("income")
    public void income() {

    }

    //冲账
    @RequestMapping("reverse")
    public void reverse() {

    }
}
