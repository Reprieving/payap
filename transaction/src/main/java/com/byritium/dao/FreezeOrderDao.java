package com.byritium.dao;

import com.byritium.entity.transaction.TransactionFreezeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreezeOrderDao extends PagingAndSortingRepository<TransactionFreezeOrder, String> {
    TransactionFreezeOrder findByBizOrderId(String bizOrderId);
}
