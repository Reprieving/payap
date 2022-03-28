package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionSettleOrderDao;
import com.byritium.dto.*;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentPayRpc;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.common.RpcRspService;
import com.byritium.service.transaction.TransactionOrderService;
import org.springframework.beans.BeanUtils;
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
    private TransactionOrderService transactionOrderService;

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private RpcRspService<PaymentResult> rpcRspService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();
        TransactionOrder transactionOrder = transactionOrderService.findByBusinessOrderId(param.getBusinessOrderId());

        Assert.notNull(transactionOrder, () -> "未找到交易订单");

        TransactionType transactionType = transactionOrder.getTransactionType();
        Assert.isTrue(transactionType != TransactionType.GUARANTEE && transactionType != TransactionType.INSTANT, () -> "交易订单不可结算");

        TransactionOrder transactionSettleOrder = new TransactionOrder();
        BeanUtils.copyProperties(transactionOrder, transactionSettleOrder);
        transactionSettleOrder.setTransactionType(type());
        transactionSettleOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionSettleOrder.setPaymentState(PaymentState.PAYMENT_PENDING);

        transactionOrderService.save(transactionSettleOrder);

        //TODO
        TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder();

        ResponseBody<PaymentResult> responseBody = paymentPayRpc.settle(transactionPaymentOrder);
        PaymentResult paymentResult = rpcRspService.get(responseBody);

        PaymentState state = paymentResult.getState();

        if (PaymentState.PAYMENT_SUCCESS == state) {
            //入账
            AccountJournal accountJournal = new AccountJournal();
            accountRpc.record(accountJournal);
        }
        transactionResult.setTransactionOrderId(transactionSettleOrder.getId());
        transactionResult.setPaymentState(paymentResult.getState());

        return transactionResult;
    }
}
