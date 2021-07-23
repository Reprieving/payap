package com.byritium.controller;

import com.byritium.entity.PaymentOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @RequestMapping("pay")
    public void pay(@RequestBody PaymentOrder paymentOrder) {


    }

    @RequestMapping("refund")
    public void refund() {

    }

    @RequestMapping("settle")
    public void settle() {

    }

    @RequestMapping("query")
    public void query() {

    }

}
