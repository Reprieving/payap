package com.byritium.controller;

import com.byritium.BusinessType;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.FreezeParam;
import com.byritium.dto.transaction.UnFreezeParam;
import com.byritium.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping("freeze")
    public TransactionResult freeze(@RequestBody FreezeParam freezeParam) {
        Long uid = freezeParam.getUid();
        BusinessType businessType = freezeParam.getBizType();
        BigDecimal amount = freezeParam.getFreezeAmount();
        return authService.free(uid, businessType, amount);
    }

    @RequestMapping("unfreeze")
    public TransactionResult unfreeze(@RequestBody UnFreezeParam unFreezeParam) {
        Long uid = unFreezeParam.getUid();
        Long freeOrderId = unFreezeParam.getFreezeOrderId();
        BigDecimal amount = unFreezeParam.getUnfreezeAmount();
        return authService.unfree(uid, freeOrderId, amount);
    }
}
