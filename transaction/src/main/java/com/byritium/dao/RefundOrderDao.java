package com.byritium.dao;

import com.byritium.entity.transaction.RefundOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface RefundOrderDao extends PagingAndSortingRepository<RefundOrder, String> {
    BigDecimal countByPayOrderId(String payOrderId);
}
