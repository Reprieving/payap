package com.byritium.controller;

import com.byritium.constance.PaymentChannel;
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

    @RequestMapping("guarantee")
    public ResponseEntity<?> guarantee(@RequestBody GuaranteeTransaction guaranteeTransaction) {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("instant")
    public ResponseEntity<?> instant(@RequestBody InstantTransaction instantTransaction) {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositTransaction depositTransaction) {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawTransaction withdrawTransaction) {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("settle")
    public ResponseEntity<?> settle(@RequestBody SettleTransaction settleTransaction) {
        return ResponseEntity.of(Optional.of("hello"));
    }

}
