package com.byritium.service.transaction;

import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.mapper.TransactionTradeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionOrderService {

    @Autowired
    private TransactionTradeOrderMapper transactionTradeOrderMapper;

    public int save(TransactionTradeOrder transactionTradeOrder) {
        return transactionTradeOrderMapper.insert(transactionTradeOrder);
    }


}
