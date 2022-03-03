package com.byritium.service;

import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionReceiptOrderRepository;
import com.byritium.dao.TransactionSettleOrderRepository;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionReceiptOrder;
import com.byritium.entity.TransactionSettleOrder;
import com.byritium.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.function.Supplier;

@Service
public class SettleTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.SETTLE;
    }

    @Resource
    private TransactionReceiptOrderRepository transactionReceiptOrderRepository;

    @Resource
    private TransactionSettleOrderRepository transactionSettleOrderRepository;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();
        TransactionReceiptOrder transactionOrder = transactionReceiptOrderRepository.findByBusinessOrderId(param.getBusinessOrderId());

        Assert.notNull(transactionOrder, () -> "未找到交易订单");

        TransactionType transactionType = transactionOrder.getTransactionType();
        Assert.isTrue(transactionType != TransactionType.GUARANTEE && transactionType != TransactionType.INSTANT, () -> "交易订单不可结算");

        TransactionSettleOrder transactionSettleOrder = new TransactionSettleOrder(
                clientId, transactionOrder.getId(), param.getReceiverIds(), param.getOrderAmount(), transactionOrder.getPaymentChannel()
        );

        transactionSettleOrderRepository.save(transactionSettleOrder);


        return transactionResult;
    }
}
