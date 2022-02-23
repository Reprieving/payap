package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionOrderRepository;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstantTransactionService implements ITransactionService {
    @Autowired
    private TransactionPayOrderService transactionPayOrderService;

    @Resource
    private TransactionOrderRepository transactionOrderRepository;

    @Override
    public TransactionType type() {
        return TransactionType.INSTANT;
    }

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        PaymentChannel paymentChannel = param.getPaymentChannel();
        TransactionOrder transactionOrder = new TransactionOrder(clientId, param);

        List<TransactionPayOrder> transactionOrderList = new ArrayList<>();

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                transactionOrderRepository.save(transactionOrder);

                String userId = param.getUserId();
                String transactionOrderId = transactionOrder.getId();

                if (paymentChannel != null) {
                    transactionOrderList.add(
                            transactionPayOrderService.saveOrder(transactionOrderId, paymentChannel, BigDecimal.ZERO, null, null)
                    );
                }
            }
        });

        if (transactionOrderList.size() > 0) {
            transactionPayOrderService.payOrder(transactionOrderList.get(0));
        }

        return transactionResult;
    }
}
