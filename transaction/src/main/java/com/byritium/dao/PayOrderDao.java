package com.byritium.dao;

import com.byritium.constance.PaymentPattern;
import com.byritium.entity.transaction.TransactionPayOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayOrderDao extends PagingAndSortingRepository<TransactionPayOrder, String> {
    List<TransactionPayOrder> findByTransactionOrderId(String orderId);

    TransactionPayOrder findByTransactionOrderIdAndPaymentChannel(String orderId, PaymentPattern paymentPattern);
}
