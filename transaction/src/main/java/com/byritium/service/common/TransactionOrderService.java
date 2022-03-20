package com.byritium.service.common;

import com.byritium.dao.TransactionOrderDao;
import com.byritium.entity.TransactionOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TransactionOrderService {

    @Resource
    private TransactionOrderDao transactionOrderDao;

    public TransactionOrder save(TransactionOrder transactionOrder) {
        return transactionOrderDao.save(transactionOrder);
    }

    public TransactionOrder findByBusinessOrderId(String orderId) {
        return transactionOrderDao.findByBusinessOrderId(orderId);
    }
}
