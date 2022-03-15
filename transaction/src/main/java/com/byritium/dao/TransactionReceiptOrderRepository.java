package com.byritium.dao;

import com.byritium.entity.TransactionOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionReceiptOrderRepository extends PagingAndSortingRepository<TransactionOrder, String> {
    TransactionOrder findByBusinessOrderId(String businessOrderId);
}
