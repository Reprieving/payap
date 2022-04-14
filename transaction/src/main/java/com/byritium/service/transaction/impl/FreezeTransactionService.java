package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.transaction.FreezeOrder;
import com.byritium.service.transaction.order.FreezeOrderService;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FreezeTransactionService implements ITransactionService {
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
        FreezeOrder freezeOrder = new FreezeOrder(
                clientId, businessOrderId, userId, orderAmount);
        PaymentChannel paymentChannel = param.getPaymentChannel();

        freezeOrderService.save(freezeOrder);


        return transactionResult;
    }

}
