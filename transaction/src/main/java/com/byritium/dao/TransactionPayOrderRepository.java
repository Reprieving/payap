package com.byritium.dao;

import com.byritium.constance.PaymentChannel;
import com.byritium.entity.TransactionPayOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionPayOrderRepository extends PagingAndSortingRepository<TransactionPayOrder, String> {
    TransactionPayOrder findByTransactionOrderIdAndPaymentChannel(String orderId, PaymentChannel paymentChannel);
}
