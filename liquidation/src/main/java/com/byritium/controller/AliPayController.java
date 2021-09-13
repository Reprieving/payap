package com.byritium.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AliPayController {
    @RequestMapping("pay")
    public void action(String businessOrderId) {

    }
}
