package com.byritium.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TransactionController {
    @RequestMapping("hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.of(Optional.of("hello"));
    }
}
