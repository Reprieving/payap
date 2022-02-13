package com.byritium.controller;

import com.byritium.dto.*;
import com.byritium.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @RequestMapping("call")
    public TransactionResult call(@RequestHeader String clientId, @RequestBody TransactionParam request) {
        return transactionService.call(clientId, request);
    }

    @RequestMapping("pay")
    public TransactionResult pay(@RequestBody TransactionParam request) {
        return null;
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
