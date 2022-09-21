package com.byritium.controller;

import com.byritium.dto.PayParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthPayController {

    @RequestMapping("freeze")
    public void freeze(@RequestBody PayParam param) {

    }

    @RequestMapping("pay")
    public void pay(@RequestBody PayParam param) {

    }

    @RequestMapping("unfreeze")
    public void unfreeze(@RequestBody PayParam param) {

    }

    @RequestMapping("query")
    public void query(@RequestBody PayParam param) {

    }

    @RequestMapping("close")
    public void close(@RequestBody PayParam param) {

    }

    @RequestMapping("notice")
    public void notice(@RequestBody PayParam param) {

    }
}
