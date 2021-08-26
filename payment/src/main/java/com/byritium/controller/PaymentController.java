package com.byritium.controller;

import com.byritium.constance.PaymentState;
import com.byritium.dto.PaymentRequest;
import com.byritium.entity.PaymentOrder;
import com.byritium.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class PaymentController {
    @Autowired
    private PaymentOrderService paymentOrderService;

    @RequestMapping("pay")
    public void pay(@RequestBody PaymentRequest paymentRequest) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setPaymentState(PaymentState.PAYMENT_PENDING);
        paymentOrderService.pay(paymentOrder);
    }

    @RequestMapping("refund")
    public void refund(@RequestBody PaymentRequest paymentRequest) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrderService.refund(paymentOrder);
    }

    @RequestMapping("withdraw")
    public void withdraw(@RequestBody PaymentRequest paymentRequest) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrderService.withdraw(paymentOrder);
    }

    @RequestMapping("query")
    public void query(@RequestBody PaymentRequest paymentRequest) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrderService.query(paymentOrder);
    }

    @RequestMapping("settle")
    public void settle(@RequestBody PaymentRequest paymentRequest) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrderService.settle(paymentOrder);
    }



}
