package com.byritium.service.transaction.impl;

import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionUnFreezeOrderDao;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.FreezeOrder;
import com.byritium.entity.UnFreezeOrder;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.ITransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FreeTransactionService implements ITransactionService {
    public FreeTransactionService(FreezeTransactionService freezeTransactionService, TransactionUnFreezeOrderDao transactionUnFreezeOrderDao, PaymentService paymentService) {
        this.freezeTransactionService = freezeTransactionService;
        this.transactionUnFreezeOrderDao = transactionUnFreezeOrderDao;
        this.paymentService = paymentService;
    }

    @Override
    public TransactionType type() {
        return TransactionType.UNFREEZE;
    }

    private final FreezeTransactionService freezeTransactionService;
    private final TransactionUnFreezeOrderDao transactionUnFreezeOrderDao;
    private final PaymentService paymentService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        String businessOrderId = param.getBusinessOrderId();
        String userId = param.getUserId();
        BigDecimal orderAmount = param.getOrderAmount();

        FreezeOrder freezeOrder = freezeTransactionService.getByBizOrderId(businessOrderId);

        UnFreezeOrder unFreezeOrder = new UnFreezeOrder(
                clientId, businessOrderId, userId, orderAmount);
        transactionUnFreezeOrderDao.save(unFreezeOrder);


        return transactionResult;
    }
}
