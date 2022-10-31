package com.byritium.controller;

import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.TransferParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @RequestMapping("freeze")
    public TransactionResult freeze() {
        return null;
    }

    @RequestMapping("unfreeze")
    public TransactionResult unfreeze() {
        return null;
    }
}
