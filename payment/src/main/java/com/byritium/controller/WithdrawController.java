package com.byritium.controller;

import com.byritium.constance.PaymentState;
import com.byritium.dto.PaymentRequest;
import com.byritium.entity.PaymentOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("withdraw")
public class WithdrawController {
    @RequestMapping("action")
    public void action(@RequestBody PaymentRequest paymentRequest) {

    }

    @RequestMapping("query")
    public void query(@RequestBody PaymentRequest paymentRequest) {

    }
}
