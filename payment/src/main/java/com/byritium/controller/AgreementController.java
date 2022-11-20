package com.byritium.controller;

import com.byritium.dto.PaymentParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("agreement")
public class AgreementController {

    @RequestMapping("sign")
    public void sign(@RequestBody PaymentParam param) {

    }

    @RequestMapping("query")
    public void query(@RequestBody PaymentParam param) {

    }

    @RequestMapping("unsign")
    public void unsign(@RequestBody PaymentParam param) {

    }
}
