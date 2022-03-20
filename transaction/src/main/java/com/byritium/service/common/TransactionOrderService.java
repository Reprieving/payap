package com.byritium.service.common;

import com.byritium.dao.TransactionOrderRepository;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPaymentOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TransactionOrderService {

    @Resource
    private TransactionOrderRepository transactionOrderRepository;

    public TransactionOrder save(TransactionOrder transactionOrder) {
        return transactionOrderRepository.save(transactionOrder);
    }

    public TransactionOrder findByBusinessOrderId(String orderId) {
        return transactionOrderRepository.findByBusinessOrderId(orderId);
    }
}
