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
    @RequestMapping("common")
    public TransactionResult refundCommon(@RequestHeader String clientId, @RequestBody TradeParam request) {
        return null;
    }

    @RequestMapping("exception")
    public TransactionResult refundException(@RequestHeader String clientId, @RequestBody RefundExceptionTxReq request) {
        return null;
    }
}
