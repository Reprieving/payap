package com.byritium.service;

import com.byritium.constance.Os;
import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import com.byritium.entity.TransactionClientAgreement;
import com.byritium.entity.TransactionOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

@Service
public class TransactionOrderService {

    private void guarantee(String clientId, String businessOrderId, BigDecimal orderAmount, BigDecimal payAmount, String subject, String payeeId, String payerId, PaymentChannel paymentChannel) {
        TransactionClientAgreement agreement = new TransactionClientAgreement();

        TransactionOrder transactionOrder = new TransactionOrder();
        transactionOrder.setClientId(clientId);
        transactionOrder.setBusinessOrderId(businessOrderId);

        if (!StringUtils.hasText(agreement.getPayeeId())) {
            transactionOrder.setPayeeId(payeeId);
        }

        if (!StringUtils.hasText(agreement.getPayerId())) {
            transactionOrder.setPayerId(payerId);
        }
        transactionOrder.setOrderAmount(orderAmount);
        transactionOrder.setPayAmount(payAmount);
        transactionOrder.setPaymentChannel(paymentChannel);
        transactionOrder.setSubject(subject);
        transactionOrder.setTransactionType(TransactionType.GUARANTEE);
        transactionOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));


    }

    private void instant() {

    }
}
