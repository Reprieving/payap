package com.byritium.service.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionFreezeOrderRepository;
import com.byritium.dao.TransactionTransferOrderRepository;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionFreezeOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.service.ITransactionService;
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
    private TransactionFreezeOrderRepository transactionFreezeOrderRepository;

    @Resource
    private AccountRpc accountRpc;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        String businessOrderId = param.getBusinessOrderId();
        String userId = param.getUserId();
        BigDecimal orderAmount = param.getOrderAmount();
        TransactionFreezeOrder transactionFreezeOrder = new TransactionFreezeOrder(
                clientId, businessOrderId, userId, orderAmount);
        transactionFreezeOrderRepository.save(transactionFreezeOrder);

        AccountJournal accountJournal = new AccountJournal();
        accountRpc.record(accountJournal);

        return transactionResult;
    }
}
