package com.byritium.dao;

import com.byritium.entity.RefundOrder;
import com.byritium.entity.SettleOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SettleOrderDao extends PagingAndSortingRepository<SettleOrder, String> {
    BigDecimal countByPayOrderId(String payOrderId);
}
