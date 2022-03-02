package com.byritium.dao;

import com.byritium.entity.TransactionReceiptOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionOrderRepository extends PagingAndSortingRepository<TransactionReceiptOrder, String> {
    TransactionReceiptOrder findByBusinessOrderId(String businessOrderId);
}
