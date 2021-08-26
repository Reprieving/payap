package com.byritium.controller;

import com.byritium.dto.PaymentRequest;
import com.byritium.entity.PaymentOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("settle")
public class SettleController {
    @RequestMapping("action")
    public void action(@RequestBody PaymentRequest paymentRequest) {
        PaymentOrder paymentOrder = new PaymentOrder();
    }

}
