package com.byritium.controller;

import com.byritium.BusinessType;
import com.byritium.constance.account.ExamineFlag;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.*;
import com.byritium.service.transaction.TransactionService;
import com.byritium.service.transaction.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("launch")
public class LaunchController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    WithdrawService withdrawService;

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
        Long uid = param.getUid();
        Long withdrawOrderId = param.getWithdrawOrderId();
        Long examinerId = param.getExaminerId();
        ExamineFlag flag = param.getFlag();
        String remark = param.getRemark();
        return withdrawService.execute(uid, withdrawOrderId, examinerId, flag, remark);
    }

    @RequestMapping("transfer")
    public TransactionResult transfer(@RequestBody TransferParam param) {
        return transactionService.transfer(param);
    }


}
