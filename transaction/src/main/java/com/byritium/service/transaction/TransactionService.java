package com.byritium.service.transaction;

import com.byritium.dto.transaction.TradeParam;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.service.TransactionPaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionOrderService transactionOrderService;

    @Autowired
    private TransactionPaymentOrderService transactionPaymentOrderService;

    public void trade(TradeParam param) {
        TransactionTradeOrder transactionTradeOrder = new TransactionTradeOrder();
        transactionTradeOrder.setClientId(param.getClientId());
        transactionTradeOrder.setBizOrderId(param.getBizOrderId());
        transactionTradeOrder.setUid(param.getClientId());
        transactionTradeOrder.setPayeeId(param.getClientId());
        transactionTradeOrder.setPayeeId(param.getClientId());
        transactionTradeOrder.setSubject(param.getSubject());
        transactionTradeOrder.setOrderAmount(param.getOrderAmount());
        transactionTradeOrder.setPaymentPatternId(param.getPaymentPatternId());
    }


}
