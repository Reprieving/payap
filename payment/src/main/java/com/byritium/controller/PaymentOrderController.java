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
    public void pay() {

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
