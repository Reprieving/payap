package com.byritium.controller;


import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PayParam;
import com.byritium.entity.PayOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {
    @RequestMapping("pay")
    public void pay(@RequestBody PayParam param) {

    }

    @RequestMapping("refund")
    public void refund(@RequestBody PayParam param) {

    }

    @RequestMapping("transfer")
    public void transfer(@RequestBody PayParam param) {

    }

    @RequestMapping("withdraw")
    public void withdraw(@RequestBody PayParam param) {

    }
}
