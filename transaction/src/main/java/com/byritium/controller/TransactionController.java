package com.byritium.controller;

import com.byritium.dto.*;
import com.byritium.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @RequestMapping("create")
    public TransactionResult guarantee(@RequestBody TransactionRequest request) {
        return transactionService.create();
    }

    @RequestMapping("pay")
    public TransactionResult pay(@RequestBody TransactionRequest request) {
        return null;
    }

    @RequestMapping("query")
    public TransactionResult query(@RequestBody TransactionRequest request) {
        return null;
    }

    @RequestMapping("notice/{channel}")
    public Object notice(@PathVariable("channel") String channel) {
        return null;
    }

}
