package com.byritium.dao;

import com.byritium.entity.TradeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOrderDao extends PagingAndSortingRepository<TradeOrder, String> {
    TradeOrder findByBizOrderId(String businessOrderId);
}
