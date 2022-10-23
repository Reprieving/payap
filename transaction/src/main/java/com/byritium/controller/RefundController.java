package com.byritium.controller;

import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("refund")
public class RefundController {

    //收单退款
    @RequestMapping("trade")
    public TransactionResult trade(@RequestHeader String clientId, @RequestBody TradeParam request) {
        return null;
    }

    //分账退款
    @RequestMapping("settle")
    public TransactionResult common(@RequestHeader String clientId, @RequestBody TradeParam request) {
        return null;
    }

}
