package com.byritium.service.transaction.impl;

import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionFreezeOrderDao;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.FreezeOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.service.transaction.ITransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class FreezeTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.FREEZE;
    }

    @Resource
    private TransactionFreezeOrderDao transactionFreezeOrderDao;

    @Resource
    private AccountRpc accountRpc;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        String businessOrderId = param.getBusinessOrderId();
        String userId = param.getUserId();
        BigDecimal orderAmount = param.getOrderAmount();
        FreezeOrder freezeOrder = new FreezeOrder(
                clientId, businessOrderId, userId, orderAmount);
        transactionFreezeOrderDao.save(freezeOrder);

        AccountJournal accountJournal = new AccountJournal();
        accountRpc.record(accountJournal);

        return transactionResult;
    }
}
