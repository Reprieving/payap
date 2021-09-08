package com.byritium.controller;

import com.byritium.dto.TransactionRequest;
import com.byritium.service.TransactionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("order")
public class OrderController {
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

    @RequestMapping("query")
    public ResponseEntity<?> query() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("notice")
    public ResponseEntity<?> notice() {
        return ResponseEntity.of(Optional.of("hello"));
    }
}