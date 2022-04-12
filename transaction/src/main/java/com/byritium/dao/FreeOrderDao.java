package com.byritium.dao;

import com.byritium.entity.FreeOrder;
import com.byritium.entity.FreezeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FreeOrderDao extends PagingAndSortingRepository<FreeOrder, String> {
    BigDecimal countByFreezeOrderId(String payOrderId);
}
