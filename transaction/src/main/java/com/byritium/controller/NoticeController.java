package com.byritium.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("notice")
public class NoticeController {

    @RequestMapping("transaction")
    public ResponseEntity<?> transaction() {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("payment")
    public ResponseEntity<?> payment() {
        return ResponseEntity.of(Optional.of("hello"));
    }
}
