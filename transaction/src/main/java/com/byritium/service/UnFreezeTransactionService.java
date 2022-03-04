package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionFreezeOrderRepository;
import com.byritium.dao.TransactionUnFreezeOrderRepository;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionFreezeOrder;
import com.byritium.entity.TransactionTransferOrder;
import com.byritium.entity.TransactionUnFreezeOrder;
import com.byritium.rpc.AccountRpc;
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
    private TransactionUnFreezeOrderRepository transactionUnFreezeOrderRepository;

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
        transactionUnFreezeOrderRepository.save(transactionUnFreezeOrder);

        AccountJournal accountJournal = new AccountJournal();
        accountRpc.record(accountJournal);

        return transactionResult;
    }
}
