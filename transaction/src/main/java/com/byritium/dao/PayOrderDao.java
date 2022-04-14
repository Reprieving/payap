package com.byritium.dao;

import com.byritium.constance.PaymentChannel;
import com.byritium.entity.transaction.PayOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayOrderDao extends PagingAndSortingRepository<PayOrder, String> {
    List<PayOrder> findByTransactionOrderId(String orderId);

    PayOrder findByTransactionOrderIdAndPaymentChannel(String orderId, PaymentChannel paymentChannel);
}
