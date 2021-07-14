package com.byritium.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("order")
public class OrderController {
    @RequestMapping("guarantee")
    public ResponseEntity<?> guarantee() {
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

    @RequestMapping("refund")
    public ResponseEntity<?> refund() {
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
