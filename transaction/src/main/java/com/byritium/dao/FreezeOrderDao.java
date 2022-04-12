package com.byritium.dao;

import com.byritium.entity.FreezeOrder;
import com.byritium.entity.SettleOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FreezeOrderDao extends PagingAndSortingRepository<FreezeOrder, String> {
    FreezeOrder findByBizOrderId(String bizOrderId);
}
