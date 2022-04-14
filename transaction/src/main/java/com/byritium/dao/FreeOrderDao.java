package com.byritium.dao;

import com.byritium.entity.transaction.FreeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FreeOrderDao extends PagingAndSortingRepository<FreeOrder, String> {
    BigDecimal countByFreezeOrderId(String payOrderId);
}
