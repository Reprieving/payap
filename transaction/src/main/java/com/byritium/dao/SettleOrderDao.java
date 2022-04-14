package com.byritium.dao;

import com.byritium.entity.transaction.TransactionSettleOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SettleOrderDao extends PagingAndSortingRepository<TransactionSettleOrder, String> {
    BigDecimal countByPayOrderId(String payOrderId);
}
