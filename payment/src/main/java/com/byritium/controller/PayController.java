package com.byritium.controller;

import com.byritium.dto.PayParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pay")
public class PayController {

    @RequestMapping("execute")
    public void execute(@RequestBody PayParam param) {

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
