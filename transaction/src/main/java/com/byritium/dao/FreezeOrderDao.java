package com.byritium.dao;

import com.byritium.entity.transaction.FreezeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreezeOrderDao extends PagingAndSortingRepository<FreezeOrder, String> {
    FreezeOrder findByBizOrderId(String bizOrderId);
}
