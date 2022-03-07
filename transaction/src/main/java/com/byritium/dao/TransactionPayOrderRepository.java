package com.byritium.dao;

import com.byritium.constance.PaymentChannel;
import com.byritium.entity.TransactionPaymentOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionPayOrderRepository extends PagingAndSortingRepository<TransactionPaymentOrder, String> {
    TransactionPaymentOrder findByTransactionOrderIdAndPaymentChannel(String orderId, PaymentChannel paymentChannel);
}
