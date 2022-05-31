package com.byritium.controller;

import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.*;
import com.byritium.service.transaction.impl.GuaranteeTransactionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("launch")
public class LaunchController {

    private final GuaranteeTransactionService guaranteeTransactionService;

    public LaunchController(GuaranteeTransactionService guaranteeTransactionService) {
        this.guaranteeTransactionService = guaranteeTransactionService;
    }

    @RequestMapping("guarantee")
    public TransactionResult guarantee(@RequestHeader String clientId, @RequestBody GuaranteeTxReq request) {
        guaranteeTransactionService.call(request);
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


}
