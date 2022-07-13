package com.byritium.controller;


import com.byritium.dto.PayParam;
import com.byritium.dto.order.TransactionPayOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentController {

    @RequestMapping("pay")
    public void pay(@RequestBody PayParam param) {

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
