package com.byritium.dao;

import com.byritium.constance.PaymentChannel;
import com.byritium.entity.PayOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentOrderDao extends PagingAndSortingRepository<PayOrder, String> {
    List<PayOrder> findByTransactionOrderId(String orderId);

    PayOrder findByTransactionOrderIdAndPaymentChannel(String orderId, PaymentChannel paymentChannel);
}
