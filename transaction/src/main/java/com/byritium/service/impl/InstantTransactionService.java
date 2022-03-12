package com.byritium.service.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionReceiptOrderRepository;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.LiquidationParam;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.entity.TransactiontOrder;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.LiquidationRpc;
import com.byritium.service.ITransactionService;
import com.byritium.service.TransactionWrapperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Service
public class InstantTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.INSTANT;
    }

    @Resource
    private TransactionWrapperService transactionWrapperService;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private LiquidationRpc liquidationRpc;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = transactionWrapperService.trade(clientId, param);

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
