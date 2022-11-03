package com.byritium.service.transaction;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byritium.constance.account.ExamineFlag;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.WithdrawParam;
import com.byritium.entity.transaction.FreezeOrder;
import com.byritium.entity.transaction.UnfreezeOrder;
import com.byritium.entity.transaction.WithdrawExamine;
import com.byritium.entity.transaction.WithdrawOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService {
    @Autowired
    private WithdrawExamineService withdrawExamineService;

    @Autowired
    private WithdrawOrderService withdrawOrderService;

    @Autowired
    private FreezeOrderService freezeOrderService;

    @Autowired
    private UnfreezeOrderService unfreezeOrderService;

    @Autowired
    private PaymentRpc paymentRpc;

    @Autowired
    private AccountRpc accountRpc;

    public TransactionResult apply(WithdrawParam param) {
        TransactionResult transactionResult = new TransactionResult();
        FreezeOrder freezeOrder = freezeOrderService.getOne(new LambdaQueryWrapper<FreezeOrder>().eq(FreezeOrder::getBizOrderId, param.getBizOrderId()));

        WithdrawOrder withdrawOrder = new WithdrawOrder();
        withdrawOrder.setClientId(param.getClientId());
        withdrawOrder.setUid(param.getUid());
        withdrawOrder.setBizOrderId(freezeOrder.getBizOrderId());
        withdrawOrder.setSubject("");
        withdrawOrder.setWithdrawAmount(freezeOrder.getFreezeAmount());
        withdrawOrder.setPaymentSettingId(param.getPaymentSettingId());

        withdrawOrderService.save(withdrawOrder);

        WithdrawExamine withdrawExamine = new WithdrawExamine();
        withdrawExamine.setWithdrawOrderId(withdrawOrder.getId());
        withdrawExamineService.save(withdrawExamine);

        return transactionResult;
    }


    public TransactionResult examine(WithdrawParam param) {
        ExamineFlag flag = param.getFlag();
        TransactionResult transactionResult = new TransactionResult();

        WithdrawExamine withdrawExamine = withdrawExamineService.getById(param.getExamineId());
        WithdrawOrder withdrawOrder = withdrawOrderService.getById(withdrawExamine.getWithdrawOrderId());

        if (ExamineFlag.APPROVED == flag) {
            paymentRpc.withdraw(withdrawOrder);
        } else {
            UnfreezeOrder unfreezeOrder = new UnfreezeOrder();
            unfreezeOrderService.save(unfreezeOrder);
            accountRpc.unfreeze();
        }

        return transactionResult;
    }
}
