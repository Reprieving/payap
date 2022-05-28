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
    @RequestMapping("/guarantee")
    public TransactionResult refundGuarantee(@RequestHeader String clientId, @RequestBody GuaranteeTxReq request) {
        return null;
    }

    @RequestMapping("instant")
    public TransactionResult refundInstant(@RequestHeader String clientId, @RequestBody RefundInstantTxReq request) {
        return null;
    }

    @RequestMapping("withdraw")
    public TransactionResult refundWithdraw(@RequestHeader String clientId, @RequestBody RefundWithdrawTxReq request) {
        return null;
    }

    @RequestMapping("recharge")
    public TransactionResult refundRecharge(@RequestHeader String clientId, @RequestBody RefundRechargeTxReq request) {
        return null;
    }

    @RequestMapping("exception")
    public TransactionResult refundException(@RequestHeader String clientId, @RequestBody RefundExceptionTxReq request) {
        return null;
    }
}
