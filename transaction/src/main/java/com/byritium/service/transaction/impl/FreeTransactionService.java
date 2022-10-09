package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.transaction.TransactionFreeOrder;
import com.byritium.entity.transaction.TransactionFreezeOrder;
import com.byritium.service.payment.PayService;
import com.byritium.service.transaction.order.FreeOrderService;
import com.byritium.service.transaction.order.FreezeOrderService;
import com.byritium.service.transaction.ITransactionCallService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FreeTransactionService implements ITransactionCallService {
    public FreeTransactionService(FreezeTransactionService freezeTransactionService, FreezeOrderService freezeOrderService, FreeOrderService freeOrderService, PayService payService) {
        this.freezeOrderService = freezeOrderService;
        this.freeOrderService = freeOrderService;
        this.payService = payService;
    }

    @Override
    public TransactionType type() {
        return TransactionType.UNFREEZE;
    }

    private final FreezeOrderService freezeOrderService;
    private final FreeOrderService freeOrderService;
    private final PayService payService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        String businessOrderId = param.getBusinessOrderId();
        String userId = param.getUserId();
        BigDecimal orderAmount = param.getOrderAmount();
        PaymentPattern paymentPattern = param.getPaymentPattern();

        TransactionFreezeOrder transactionFreezeOrder = freezeOrderService.getByBizOrderId(businessOrderId);

        freeOrderService.verify(transactionFreezeOrder, orderAmount);

        TransactionFreeOrder transactionFreeOrder = new TransactionFreeOrder(
                clientId, businessOrderId, userId, orderAmount);
        freeOrderService.save(transactionFreeOrder);



        return transactionResult;
    }
}
