package com.byritium.service;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import com.byritium.entity.TransactionOrder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class GuaranteeTxService {

    public void guarantee(String clientId, String businessOrderId, BigDecimal orderAmount, BigDecimal payAmount, String subject) {
        TransactionOrder transactionOrder = new TransactionOrder();
        transactionOrder.setClientId(clientId);
        transactionOrder.setBusinessOrderId(businessOrderId);
        transactionOrder.setOrderAmount(orderAmount);
        transactionOrder.setPayAmount(payAmount);
        transactionOrder.setSubject(subject);
        transactionOrder.setTransactionType(TransactionType.GUARANTEE);
        transactionOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionOrder.setPaymentState(PaymentState.PAYMENT_PENDING);
        transactionOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));

    }

    private void instant() {

    }
}
