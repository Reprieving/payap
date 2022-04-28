package com.byritium.controller;

import com.byritium.dto.*;
import com.byritium.service.transaction.TransactionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    @Autowired
    private TransactionManagerService transactionManagerService;


    @RequestMapping("pay")
    public TransactionResult pay(@RequestHeader String clientId,@RequestBody TransactionParam request) {
        return transactionManagerService.pay(clientId,request);
    }

    @RequestMapping("query")
    public TransactionResult query(@RequestBody TransactionParam request) {
        return null;
    }

    @RequestMapping("notice/{channel}")
    public Object notice(@PathVariable("channel") String channel) {
        return null;
    }

}
