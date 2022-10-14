package com.byritium.service;

import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.mapper.TransactionPaymentOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionPaymentOrderService {

    @Autowired
    private TransactionPaymentOrderMapper transactionPaymentOrderMapper;

    public int save(TransactionPaymentOrder transactionPaymentOrder) {
        return transactionPaymentOrderMapper.insert(transactionPaymentOrder);
    }


}
