package com.byritium.controller;

import com.byritium.dto.*;
import com.byritium.service.TransactionManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    @Autowired
    private TransactionManagerService transactionManagerService;

    @RequestMapping("call")
    public TransactionResult call(@RequestHeader String clientId, @RequestBody TransactionParam request) {
        return transactionManagerService.call(clientId, request);
    }

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
