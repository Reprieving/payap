package com.byritium.controller;


import com.byritium.dto.order.TransactionPayOrder;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class PaymentOrderController {
    @RequestMapping("pay")
    public void pay(@RequestBody List<TransactionPayOrder> orders) {

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
