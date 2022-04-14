package com.byritium.dao;

import com.byritium.entity.transaction.TransactionTradeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOrderDao extends PagingAndSortingRepository<TransactionTradeOrder, String> {
    TransactionTradeOrder findByBizOrderId(String businessOrderId);
}
