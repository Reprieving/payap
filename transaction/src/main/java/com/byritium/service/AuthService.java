package com.byritium.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byritium.BusinessType;
import com.byritium.dto.TransactionInfo;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.FreezeParam;
import com.byritium.dto.transaction.UnFreezeParam;
import com.byritium.entity.transaction.FreezeOrder;
import com.byritium.entity.transaction.UnfreezeOrder;
import com.byritium.entity.transaction.WithdrawOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.service.transaction.FreezeOrderService;
import com.byritium.service.transaction.UnfreezeOrderService;
import com.byritium.service.transaction.WithdrawOrderService;
import com.byritium.utils.TrxInfoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {

    @Autowired
    private FreezeOrderService freezeOrderService;

    @Autowired
    private UnfreezeOrderService unfreezeOrderService;

    @Autowired
    private WithdrawOrderService withdrawOrderService;

    @Autowired
    private AccountRpc accountRpc;

    public TransactionResult free(Long uid, BusinessType businessType, BigDecimal amount) {
        TransactionInfo transactionInfo = TrxInfoHolder.get();
        TransactionResult transactionResult = new TransactionResult();
        FreezeOrder freezeOrder = new FreezeOrder();
        freezeOrder.setClientId(transactionInfo.getClientId());
        freezeOrder.setBizOrderId(transactionInfo.getBizOrderId());
        freezeOrder.setUid(uid);
        freezeOrder.setBizType(businessType);
        freezeOrder.setFreezeAmount(amount);

        freezeOrderService.save(freezeOrder);

        switch (businessType) {
            case FREE_WITHDRAW:
                WithdrawOrder withdrawOrder = new WithdrawOrder();
                withdrawOrder.setClientId(transactionInfo.getClientId());
                withdrawOrder.setUid(uid);
                withdrawOrder.setBizOrderId(transactionInfo.getBizOrderId());
                withdrawOrder.setFreeOrderId(freezeOrder.getId());
                withdrawOrder.setSubject("");
                withdrawOrder.setWithdrawAmount(amount);
                withdrawOrder.setPaymentSettingId(transactionInfo.getPaymentSettingId());
                withdrawOrderService.save(withdrawOrder);
                break;
        }


        accountRpc.freeze();

        return transactionResult;
    }

    public TransactionResult unfree(Long uid, Long freeOrderId, BigDecimal amount) {
        TransactionInfo transactionInfo = TrxInfoHolder.get();
        TransactionResult transactionResult = new TransactionResult();
        FreezeOrder freezeOrder = freezeOrderService.getOne(new LambdaQueryWrapper<FreezeOrder>().eq(FreezeOrder::getBizOrderId, freeOrderId));
        UnfreezeOrder unfreezeOrder = new UnfreezeOrder();
        unfreezeOrder.setClientId(transactionInfo.getClientId());
        unfreezeOrder.setBizOrderId(transactionInfo.getBizOrderId());
        unfreezeOrder.setFreezeOrderId(freeOrderId);
        unfreezeOrder.setUid(uid);
        unfreezeOrder.setBizType(freezeOrder.getBizType());
        unfreezeOrder.setFreezeAmount(amount);

        unfreezeOrderService.save(unfreezeOrder);

        accountRpc.unfreeze();

        return transactionResult;
    }
}
