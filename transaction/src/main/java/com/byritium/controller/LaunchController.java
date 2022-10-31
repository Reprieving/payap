package com.byritium.controller;

import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.*;
import com.byritium.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("launch")
public class LaunchController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping("trade")
    public TransactionResult trade(@RequestBody TradeParam param) {
        return transactionService.trade(param);
    }

    @RequestMapping("recharge")
    public TransactionResult recharge(@RequestBody RechargeParam param) {
        return transactionService.recharge(param);
    }

    @RequestMapping("withdraw")
    public TransactionResult withdraw(@RequestBody WithdrawParam param) {
        return transactionService.withdraw(param);
    }

    @RequestMapping("transfer")
    public TransactionResult transfer(@RequestBody TransferParam param) {
        return transactionService.transfer(param);
    }




}
