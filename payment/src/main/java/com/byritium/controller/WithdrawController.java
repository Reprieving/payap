package com.byritium.controller;

import com.byritium.dto.PayParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("withdraw")
public class WithdrawController {

    @RequestMapping("orders")
    public void orders(@RequestBody PayParam param) {

    }

    @RequestMapping("query")
    public void query(@RequestBody PayParam param) {

    }
}
