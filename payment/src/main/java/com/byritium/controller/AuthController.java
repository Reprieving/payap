package com.byritium.controller;

import com.byritium.dto.PaymentParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @RequestMapping("freeze")
    public void freeze(@RequestBody PaymentParam param) {

    }

    @RequestMapping("unfreeze")
    public void unfreeze(@RequestBody PaymentParam param) {

    }

    @RequestMapping("query")
    public void query(@RequestBody PaymentParam param) {

    }

    @RequestMapping("cancel")
    public void cancel(@RequestBody PaymentParam param) {

    }
}
