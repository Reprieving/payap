package com.byritium.controller;

import com.byritium.dto.PaymentParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settle")
public class SettleController {

    @RequestMapping("execute")
    public void execute(@RequestBody PaymentParam param) {

    }

    @RequestMapping("query")
    public void queryOrders(@RequestBody PaymentParam param) {

    }


}
