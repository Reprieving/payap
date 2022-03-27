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

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private LiquidationRpc liquidationRpc;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = transactionOrderService.trade(clientId, param);

        if (PaymentState.PAYMENT_SUCCESS == transactionResult.getPaymentState()) {
            //支付入账
            AccountJournal accountJournal = new AccountJournal();
            accountRpc.record(accountJournal);

            //清算
            LiquidationParam liquidationParam = new LiquidationParam();
            liquidationRpc.payment(liquidationParam);
        }


        return transactionResult;
    }
}
