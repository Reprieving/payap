package com.byritium.dao;

import com.byritium.constance.PaymentChannel;
import com.byritium.entity.TransactionPaymentOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionPaymentOrderRepository extends PagingAndSortingRepository<TransactionPaymentOrder, String> {
    List<TransactionPaymentOrder> findByTransactionOrderId(String orderId);

    TransactionPaymentOrder findByTransactionOrderIdAndPaymentChannel(String orderId, PaymentChannel paymentChannel);
}
