package com.byritium.controller;

import com.byritium.dto.*;
import com.byritium.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @RequestMapping("create")
    public Object guarantee(@RequestBody GuaranteeTransaction guaranteeTransaction) {
        return null;
    }

    @RequestMapping("query")
    public Object query(@RequestBody GuaranteeTransaction guaranteeTransaction) {
        return null;
    }

    @RequestMapping("notice")
    public Object notice(@RequestBody GuaranteeTransaction guaranteeTransaction) {
        return null;
    }
}
