package com.byritium.controller;

import com.byritium.dto.PaymentParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("refund")
public class RefundController {

    @RequestMapping("orders")
    public void orders(@RequestBody PaymentParam param) {

    }

    @RequestMapping("query")
    public void query(@RequestBody PaymentParam param) {

    }
}
