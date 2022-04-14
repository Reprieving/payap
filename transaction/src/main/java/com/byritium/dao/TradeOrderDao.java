package com.byritium.dao;

import com.byritium.entity.transaction.TradeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOrderDao extends PagingAndSortingRepository<TradeOrder, String> {
    TradeOrder findByBizOrderId(String businessOrderId);
}
