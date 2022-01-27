package com.byritium.controller;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.GuaranteeTransaction;
import com.byritium.dto.InstantTransaction;
import com.byritium.dto.SettleTransaction;
import com.byritium.dto.TransactionRequest;
import com.byritium.service.TransactionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TransactionController {
    @Autowired
    private TransactionOrderService transactionOrderService;

    @RequestMapping("guarantee")
    public ResponseEntity<?> guarantee(@RequestBody GuaranteeTransaction guaranteeTransaction) {

        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("instant")
    public ResponseEntity<?> instant(@RequestBody InstantTransaction instantTransaction) {
        return ResponseEntity.of(Optional.of("hello"));
    }

    @RequestMapping("settle")
    public ResponseEntity<?> settle(@RequestBody SettleTransaction settleTransaction) {
        return ResponseEntity.of(Optional.of("hello"));
    }

}
