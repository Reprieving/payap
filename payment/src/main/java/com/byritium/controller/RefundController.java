package com.byritium.controller;

import com.byritium.dto.PaymentRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("refund")
public class RefundController {
    @RequestMapping("action")
    public void action(@RequestBody PaymentRequest paymentRequest) {

    }

    @RequestMapping("query")
    public void query(@RequestBody PaymentRequest paymentRequest) {

    }
}