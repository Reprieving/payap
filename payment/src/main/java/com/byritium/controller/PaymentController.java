package com.byritium.controller;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProductType;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionProductType;
import com.byritium.dto.PaymentRequest;
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
    public void refund() {

    }

    @RequestMapping("query")
    public void query() {

    }

    @RequestMapping("settle")
    public void settle() {

    }

}
