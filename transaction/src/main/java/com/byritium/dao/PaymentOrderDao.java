package com.byritium.dao;

import com.byritium.constance.PaymentChannel;
import com.byritium.entity.PaymentOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentOrderDao extends PagingAndSortingRepository<PaymentOrder, String> {
    List<PaymentOrder> findByTransactionOrderId(String orderId);

    PaymentOrder findByTransactionOrderIdAndPaymentChannel(String orderId, PaymentChannel paymentChannel);
}
