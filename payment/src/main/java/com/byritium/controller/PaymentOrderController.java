package com.byritium.controller;

import com.byritium.constance.PaymentStatus;
import com.byritium.dto.PaymentRequest;
import com.byritium.entity.PaymentOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class PaymentOrderController {
    @RequestMapping("pay")
    public void pay(@RequestBody PaymentRequest paymentRequest) {

    }
}
