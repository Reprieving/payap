package com.byritium.controller;

import com.byritium.constance.PaymentStatus;
import com.byritium.dto.PaymentRequest;
import com.byritium.entity.PaymentOrder;
import com.byritium.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pay")
public class PaymentController {
    @Autowired
    private PaymentOrderService paymentOrderService;

    @RequestMapping("call")
    public void call(@RequestBody PaymentRequest paymentRequest) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setPaymentStatus(PaymentStatus.PAYMENT_PENDING);
        paymentOrderService.pay(paymentOrder);
    }


}
