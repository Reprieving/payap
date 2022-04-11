package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionTransferOrderDao;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransferOrder;
import com.byritium.service.transaction.ITransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class TransferTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.TRANSFER;
    }

    @Resource
    private TransactionTransferOrderDao transactionTransferOrderDao;


    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        String businessOrderId = param.getBusinessOrderId();
        String senderId = param.getSenderId();
        String receiverId = param.getReceiverId();
        BigDecimal orderAmount = param.getOrderAmount();
        PaymentChannel paymentChannel = param.getPaymentChannel();
        TransferOrder transferOrder = new TransferOrder(
                clientId, businessOrderId, senderId, receiverId, orderAmount, paymentChannel);
        transactionTransferOrderDao.save(transferOrder);



        return transactionResult;
    }
}
