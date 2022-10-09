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

    @RequestMapping("trade")
    public TransactionResult trade(@RequestBody TradeParam param) {
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
