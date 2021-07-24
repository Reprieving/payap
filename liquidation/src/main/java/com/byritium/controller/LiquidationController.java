package com.byritium.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiquidationController {

    @RequestMapping("pay")
    public void pay(String businessOrderId) {

    }

    @RequestMapping("settle")
    public void settle(String businessOrderId) {

    }

    @RequestMapping("settle")
    public void refund(String businessOrderId) {

    }

    @RequestMapping("withdraw")
    public void withdraw(String businessOrderId) {

    }
}
