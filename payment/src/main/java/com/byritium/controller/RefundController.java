package com.byritium.controller;


import com.byritium.dto.PayParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("refund")
public class RefundController {

    @RequestMapping("")
    public void call(@RequestBody PayParam param) {

    }


}
