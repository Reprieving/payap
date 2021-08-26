package com.byritium.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fund")
public class FundController {
    @RequestMapping("freeze")
    public void frozen() {

    }

    @RequestMapping("unfreeze")
    public void unfreeze() {

    }

}
