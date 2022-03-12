package com.byritium.dao;

import com.byritium.entity.TransactiontOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionReceiptOrderRepository extends PagingAndSortingRepository<TransactiontOrder, String> {
    TransactiontOrder findByBusinessOrderId(String businessOrderId);
}
