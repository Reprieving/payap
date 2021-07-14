package com.byritium.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class OrderController {
    @RequestMapping("guarantee")
    public ResponseEntity<?> guarantee() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("instant")
    public ResponseEntity<?> instant() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("settlement")
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


}
