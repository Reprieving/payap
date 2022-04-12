package com.byritium.dao;

import com.byritium.entity.FreezeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FreeOrderDao extends PagingAndSortingRepository<FreezeOrder, String> {
    BigDecimal countByPayOrderId(String payOrderId);
}
