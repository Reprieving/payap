package com.byritium.controller;

import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.FreezeParam;
import com.byritium.dto.transaction.UnFreezeParam;
import com.byritium.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping("freeze")
    public TransactionResult freeze(@RequestBody FreezeParam freezeParam) {

        return null;
    }

    @RequestMapping("unfreeze")
    public TransactionResult unfreeze(@RequestBody UnFreezeParam unFreezeParam) {
        return null;
    }
}
