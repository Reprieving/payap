package com.byritium.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.FreezeParam;
import com.byritium.dto.transaction.UnFreezeParam;
import com.byritium.entity.transaction.FreezeOrder;
import com.byritium.entity.transaction.UnfreezeOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.service.transaction.FreezeOrderService;
import com.byritium.service.transaction.UnfreezeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private FreezeOrderService freezeOrderService;

    @Autowired
    private UnfreezeOrderService unfreezeOrderService;

    @Autowired
    private AccountRpc accountRpc;

    public TransactionResult free(FreezeParam freezeParam) {
        TransactionResult transactionResult = new TransactionResult();
        FreezeOrder freezeOrder = new FreezeOrder();
        freezeOrder.setClientId(freezeParam.getClientId());
        freezeOrder.setBizOrderId(freezeOrder.getBizOrderId());
        freezeOrder.setUid(freezeParam.getUid());
        freezeOrder.setBizType(freezeParam.getBizType());
        freezeOrder.setFreezeAmount(freezeParam.getFreezeAmount());

        freezeOrderService.save(freezeOrder);

        accountRpc.freeze();

        return transactionResult;
    }

    public TransactionResult unfree(UnFreezeParam unFreezeParam) {
        TransactionResult transactionResult = new TransactionResult();
        FreezeOrder freezeOrder = freezeOrderService.getOne(new LambdaQueryWrapper<FreezeOrder>().eq(FreezeOrder::getBizOrderId, unFreezeParam.getBizOrderId()));
        UnfreezeOrder unfreezeOrder = new UnfreezeOrder();
        unfreezeOrder.setClientId(freezeOrder.getClientId());
        unfreezeOrder.setBizOrderId(freezeOrder.getBizOrderId());
        unfreezeOrder.setFreezeOrderId(freezeOrder.getId());
        unfreezeOrder.setUid(freezeOrder.getUid());
        unfreezeOrder.setBizType(freezeOrder.getBizType());
        unfreezeOrder.setFreezeAmount(freezeOrder.getFreezeAmount());

        accountRpc.unfreeze();

        return transactionResult;
    }
}
