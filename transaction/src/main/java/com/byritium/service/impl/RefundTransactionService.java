package com.byritium.service.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionPaymentOrderDao;
import com.byritium.dto.*;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.entity.TransactionOrder;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentPayRpc;
import com.byritium.service.ITransactionService;
import com.byritium.service.common.ResponseBodyService;
import com.byritium.service.common.TransactionOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RefundTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.REFUND;
    }

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private TransactionOrderService transactionOrderService;

    @Resource
    private TransactionPaymentOrderDao transactionPaymentOrderDao;

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private ResponseBodyService<PaymentResult> responseBodyService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        TransactionOrder transactionOrder = transactionOrderService.findByBusinessOrderId(param.getBusinessOrderId());
        if (transactionOrder == null) {
            throw new BusinessException("未找到订单");
        }

        if (param.getOrderAmount().compareTo(transactionOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("退款金额异常");
        }

        TransactionState transactionState = transactionOrder.getTransactionState();
        String transactionOrderId = transactionOrder.getId();
        PaymentChannel paymentChannel = transactionOrder.getPaymentChannel();
        List<TransactionPaymentOrder> transactionPaymentOrderList = new ArrayList<>(10);
        if (transactionState == TransactionState.TRANSACTION_SUCCESS) {
            TransactionPaymentOrder transactionPaymentOrder = transactionPaymentOrderDao.findByTransactionOrderIdAndPaymentChannel(transactionOrderId, paymentChannel);
            transactionPaymentOrderList.add(transactionPaymentOrder);
        } else {
            List<TransactionPaymentOrder> paymentOrderList = transactionPaymentOrderDao.findByTransactionOrderId(transactionOrderId);
            transactionPaymentOrderList.addAll(paymentOrderList);
        }

        TransactionOrder transactionRefundOrder = new TransactionOrder();
        BeanUtils.copyProperties(transactionOrder, transactionRefundOrder);
        transactionRefundOrder.setTransactionType(type());
        transactionRefundOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionRefundOrder.setPaymentState(PaymentState.PAYMENT_PENDING);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                transactionOrderService.save(transactionRefundOrder);
                transactionPaymentOrderDao.saveAll(transactionPaymentOrderList);
            }
        });

        ResponseBody<PaymentResult> responseBody = paymentPayRpc.refund(transactionRefundOrder);
        if (responseBody.success()) {
            //入账
            AccountJournal accountJournal = new AccountJournal();
            accountRpc.record(accountJournal);
        }
        PaymentResult paymentResult = responseBodyService.get(responseBody);
        transactionResult.setTransactionOrderId(transactionRefundOrder.getId());
        transactionResult.setPaymentState(paymentResult.getState());

        return transactionResult;
    }
}
