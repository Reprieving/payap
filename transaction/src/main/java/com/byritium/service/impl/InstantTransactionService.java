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
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Service
public class InstantTransactionService implements ITransactionService {
    @Resource
    private TransactionPaymentOrderService transactionPaymentOrderService;

    @Resource
    private TransactionReceiptOrderRepository transactionReceiptOrderRepository;

    @Override
    public TransactionType type() {
        return TransactionType.INSTANT;
    }

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private LiquidationRpc liquidationRpc;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        PaymentChannel paymentChannel = param.getPaymentChannel();

        TransactiontOrder transactiontOrder = new TransactiontOrder(clientId, param);

        TransactionPaymentOrder transactionPayOrder = transactionTemplate.execute(transactionStatus -> {
            transactionReceiptOrderRepository.save(transactiontOrder);

            String userId = param.getUserId();
            String transactionOrderId = transactiontOrder.getId();

            if (paymentChannel != null) {
                return transactionPaymentOrderService.buildCoreOrder(transactionOrderId, paymentChannel, userId, transactiontOrder.getOrderAmount());
            }
            throw new BusinessException("order exception");
        });

        transactionPaymentOrderService.payOrder(transactionPayOrder);

        PaymentState state = transactionPayOrder.getState();
        transactionResult.setPaymentState(state);
        transactionPaymentOrderService.saveOrder(transactionPayOrder);
        if (state == PaymentState.PAYMENT_SUCCESS) {
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
