package com.byritium.service.transaction;

import com.byritium.entity.transaction.TransactionRechargeOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.mapper.TransactionRechargeOrderMapper;
import com.byritium.mapper.TransactionTradeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionRechargeOrderService {

    @Autowired
    private TransactionRechargeOrderMapper transactionRechargeOrderMapper;

    public int save(TransactionRechargeOrder transactionRechargeOrder) {
        return transactionRechargeOrderMapper.insert(transactionRechargeOrder);
    }


}
