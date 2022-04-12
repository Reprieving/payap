package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.FreeOrder;
import com.byritium.entity.FreezeOrder;
import com.byritium.entity.TransferOrder;
import com.byritium.service.payment.FreeOrderService;
import com.byritium.service.payment.FreezeOrderService;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.ITransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FreeTransactionService implements ITransactionService {
    public FreeTransactionService(FreezeTransactionService freezeTransactionService, FreezeOrderService freezeOrderService, FreeOrderService freeOrderService, PaymentService paymentService) {
        this.freezeOrderService = freezeOrderService;
        this.freeOrderService = freeOrderService;
        this.paymentService = paymentService;
    }

    @Override
    public TransactionType type() {
        return TransactionType.UNFREEZE;
    }

    private final FreezeOrderService freezeOrderService;
    private final FreeOrderService freeOrderService;
    private final PaymentService paymentService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        String businessOrderId = param.getBusinessOrderId();
        String userId = param.getUserId();
        BigDecimal orderAmount = param.getOrderAmount();
        PaymentChannel paymentChannel = param.getPaymentChannel();

        FreezeOrder freezeOrder = freezeOrderService.getByBizOrderId(businessOrderId);

        freeOrderService.verify(freezeOrder, orderAmount);

        FreeOrder freeOrder = new FreeOrder(
                clientId, businessOrderId, userId, orderAmount);
        freeOrderService.save(freeOrder);

        TransferOrder transferOrder = new TransferOrder(
                clientId, businessOrderId, userId, userId, orderAmount, paymentChannel);


        return transactionResult;
    }
}
