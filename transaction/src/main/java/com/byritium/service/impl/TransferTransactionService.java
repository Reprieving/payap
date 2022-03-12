package com.byritium.service.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionTransferOrderRepository;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionTransferOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentPayRpc;
import com.byritium.service.ITransactionService;
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
    private TransactionTransferOrderRepository transactionTransferOrderRepository;

    @Resource
    private AccountRpc accountRpc;

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
        transactionTransferOrderRepository.save(transactionTransferOrder);

        AccountJournal accountJournal = new AccountJournal();
        accountRpc.record(accountJournal);

        return transactionResult;
    }
}
