package com.byritium.dao;

import com.byritium.entity.TradeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionReceiptOrderDao extends PagingAndSortingRepository<TradeOrder, String> {
    TradeOrder findByBusinessOrderId(String businessOrderId);
}
