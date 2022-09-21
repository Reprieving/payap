package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.transaction.TransactionFreezeOrder;
import com.byritium.service.transaction.order.FreezeOrderService;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.ITransactionCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FreezeTransactionService implements ITransactionCallService {
    @Override
    public TransactionType type() {
        return TransactionType.FREEZE;
    }

    @Autowired
    private FreezeOrderService freezeOrderService;

    @Autowired
    private  PaymentService paymentService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        String businessOrderId = param.getBusinessOrderId();
        String userId = param.getUserId();
        BigDecimal orderAmount = param.getOrderAmount();
        TransactionFreezeOrder transactionFreezeOrder = new TransactionFreezeOrder(
                clientId, businessOrderId, userId, orderAmount);
        PaymentPattern paymentPattern = param.getPaymentPattern();

        freezeOrderService.save(transactionFreezeOrder);


        return transactionResult;
    }

}
