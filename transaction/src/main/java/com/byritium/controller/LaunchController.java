package com.byritium.controller;

import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("launch")
public class LaunchController {
    @RequestMapping("guarantee")
    public TransactionResult guarantee(@RequestHeader String clientId, @RequestBody GuaranteeTxReq request) {
        return null;
    }

    @RequestMapping("instant")
    public TransactionResult instant(@RequestHeader String clientId, @RequestBody InstantTxReq request) {
        return null;
    }

    @RequestMapping("recharge")
    public TransactionResult recharge(@RequestHeader String clientId, @RequestBody RechargeTxReq request) {
        return null;
    }

    @RequestMapping("withdraw")
    public TransactionResult withdraw(@RequestHeader String clientId, @RequestBody WithdrawTxReq request) {
        return null;
    }

    @RequestMapping("transfer")
    public TransactionResult transfer(@RequestHeader String clientId, @RequestBody TransferTxReq request) {
        return null;
    }

    @RequestMapping("refund/guarantee")
    public TransactionResult refundGurantee(@RequestHeader String clientId, @RequestBody GuaranteeTxReq request) {
        return null;
    }

    @RequestMapping("refund/instant")
    public TransactionResult refundInstant(@RequestHeader String clientId, @RequestBody RefundInstantTxReq request) {
        return null;
    }

    @RequestMapping("refund/withdraw")
    public TransactionResult refundWithdraw(@RequestHeader String clientId, @RequestBody RefundWithdrawTxReq request) {
        return null;
    }

    @RequestMapping("refund/recharge")
    public TransactionResult refundRecharge(@RequestHeader String clientId, @RequestBody RefundRechargeTxReq request) {
        return null;
    }

    @RequestMapping("refund/exception")
    public TransactionResult refundException(@RequestHeader String clientId, @RequestBody RefundExceptionTxReq request) {
        return null;
    }
}
