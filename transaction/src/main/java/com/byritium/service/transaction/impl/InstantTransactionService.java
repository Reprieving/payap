package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.LiquidationParam;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.LiquidationRpc;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.TransactionOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class InstantTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.INSTANT;
    }

    @Resource
    private TransactionOrderService transactionOrderService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = transactionOrderService.trade(clientId, param);
        return transactionResult;
    }
}
