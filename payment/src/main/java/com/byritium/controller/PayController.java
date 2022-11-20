package com.byritium.controller;

import com.byritium.dto.PaymentParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pay")
public class PayController {

    @RequestMapping("execute")
    public void execute(@RequestBody PaymentParam param) {

    }

    @RequestMapping("query")
    public void query(@RequestBody PaymentParam param) {

    }

    @RequestMapping("close")
    public void close(@RequestBody PaymentParam param) {

    }

    @RequestMapping("notice")
    public void notice(@RequestBody PaymentParam param) {

    }
}
