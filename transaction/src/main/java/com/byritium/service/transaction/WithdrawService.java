package com.byritium.service.transaction;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.WithdrawParam;
import com.byritium.entity.transaction.FreezeOrder;
import com.byritium.entity.transaction.WithdrawOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService {
    @Autowired
    private WithdrawOrderService withdrawOrderService;

    @Autowired
    private FreezeOrderService freezeOrderService;

    @Autowired
    private PaymentRpc paymentRpc;

    public TransactionResult withdraw(WithdrawParam param) {
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

        paymentRpc.withdraw(withdrawOrder);

        return transactionResult;
    }
}
