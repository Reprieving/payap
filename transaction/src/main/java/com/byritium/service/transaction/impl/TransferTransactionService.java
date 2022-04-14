package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransferOrderDao;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.transaction.TransactionTransferOrder;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.ITransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferTransactionService implements ITransactionService {
    public TransferTransactionService(TransferOrderDao transferOrderDao, PaymentService paymentService) {
        this.transferOrderDao = transferOrderDao;
        this.paymentService = paymentService;
    }

    @Override
    public TransactionType type() {
        return TransactionType.TRANSFER;
    }

    private final TransferOrderDao transferOrderDao;

    private final PaymentService paymentService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        String businessOrderId = param.getBusinessOrderId();
        String senderId = param.getSenderId();
        String receiverId = param.getReceiverId();
        BigDecimal orderAmount = param.getOrderAmount();
        PaymentChannel paymentChannel = param.getPaymentChannel();
        TransactionTransferOrder transactionTransferOrder = new TransactionTransferOrder(
                clientId, businessOrderId, senderId, receiverId, orderAmount, paymentChannel);
        transferOrderDao.save(transactionTransferOrder);


        return transactionResult;
    }
}
