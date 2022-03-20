package com.byritium.service.impl;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionReceiptOrderDao;
import com.byritium.dao.TransactionSettleOrderDao;
import com.byritium.dto.*;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionSettleOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentPayRpc;
import com.byritium.service.ITransactionService;
import com.byritium.service.common.ResponseBodyService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class SettleTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.SETTLE;
    }

    @Resource
    private TransactionReceiptOrderDao transactionReceiptOrderDao;

    @Resource
    private TransactionSettleOrderDao transactionSettleOrderDao;

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private ResponseBodyService<PaymentResult> responseBodyService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();
        TransactionOrder transactionOrder = transactionReceiptOrderDao.findByBusinessOrderId(param.getBusinessOrderId());

        Assert.notNull(transactionOrder, () -> "未找到交易订单");

        TransactionType transactionType = transactionOrder.getTransactionType();
        Assert.isTrue(transactionType != TransactionType.GUARANTEE && transactionType != TransactionType.INSTANT, () -> "交易订单不可结算");

        TransactionSettleOrder transactionSettleOrder = new TransactionSettleOrder(
                clientId, transactionOrder.getId(), param.getReceiverIds(), param.getOrderAmount(), transactionOrder.getPaymentChannel()
        );

        transactionSettleOrderDao.save(transactionSettleOrder);

        ResponseBody<PaymentResult> responseBody = paymentPayRpc.settle(transactionSettleOrder);
        PaymentResult paymentResult = responseBodyService.get(responseBody);

        PaymentState state = paymentResult.getState();

        if (PaymentState.PAYMENT_SUCCESS == state) {
            //退款入账
            AccountJournal accountJournal = new AccountJournal();
            accountRpc.record(accountJournal);
        }
        transactionResult.setTransactionOrderId(transactionSettleOrder.getId());
        transactionResult.setPaymentState(paymentResult.getState());

        return transactionResult;
    }
}
