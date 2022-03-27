package com.byritium.service.transaction.impl;

import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionUnFreezeOrderDao;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionUnFreezeOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.service.transaction.ITransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class UnFreezeTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.UNFREEZE;
    }

    @Resource
    private TransactionUnFreezeOrderDao transactionUnFreezeOrderDao;

    @Resource
    private AccountRpc accountRpc;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        String businessOrderId = param.getBusinessOrderId();
        String userId = param.getUserId();
        BigDecimal orderAmount = param.getOrderAmount();
        TransactionUnFreezeOrder transactionUnFreezeOrder = new TransactionUnFreezeOrder(
                clientId, businessOrderId, userId, orderAmount);
        transactionUnFreezeOrderDao.save(transactionUnFreezeOrder);

        AccountJournal accountJournal = new AccountJournal();
        accountRpc.record(accountJournal);

        return transactionResult;
    }
}
