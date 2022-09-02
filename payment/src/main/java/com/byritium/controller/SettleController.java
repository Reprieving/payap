package com.byritium.controller;

import com.byritium.dto.PayParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settle")
public class SettleController {

    @RequestMapping("orders")
    public void orders(@RequestBody PayParam param) {

    }

    @RequestMapping("orders/query")
    public void queryOrders(@RequestBody PayParam param) {

    }

    @RequestMapping("receiver/add")
    public void addReceiver(@RequestBody PayParam param) {

    }

    @RequestMapping("receiver/delete")
    public void deleteReceiver(@RequestBody PayParam param) {

    }
}
