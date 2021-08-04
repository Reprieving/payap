package com.byritium.controller;

import com.byritium.dto.TransactionRequest;
import com.byritium.service.TransactionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("order")
public class TransactionController {

    @Autowired
    private TransactionOrderService transactionOrderService;

    @RequestMapping("guarantee")
    public ResponseEntity<?> guarantee(@RequestBody TransactionRequest transactionRequest) {

        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("instant")
    public ResponseEntity<?> instant() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("goon")
    public ResponseEntity<?> goon() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("settle")
    public ResponseEntity<?> settlement() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("query")
    public ResponseEntity<?> query() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("transfer")
    public ResponseEntity<?> transfer() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("freeze")
    public ResponseEntity<?> frozen() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("unfreeze")
    public ResponseEntity<?> unfreeze() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("recharge")
    public ResponseEntity<?> recharge() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("withdraw")
    public ResponseEntity<?> withdraw() {
        return ResponseEntity.of(Optional.of("hello"));
    }
}
