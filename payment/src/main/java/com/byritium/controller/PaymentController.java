package com.byritium.controller;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProductType;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionProductType;
import com.byritium.entity.PaymentOrder;
import com.byritium.entity.PaymentProduct;
import com.byritium.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PaymentController {

    @Autowired
    private PaymentOrderService paymentOrderService;


    @RequestMapping("pay")
    public void pay(@RequestBody PaymentOrder paymentOrder) {
        paymentOrder.setPaymentState(PaymentState.PAYMENT_PENDING);
        paymentOrderService.pay(paymentOrder);
    }

    @RequestMapping("settle")
    public void settle() {

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

    @RequestMapping("freeze")
    public void frozen() {

    }

    @RequestMapping("unfreeze")
    public void unfreeze() {

    }
}
