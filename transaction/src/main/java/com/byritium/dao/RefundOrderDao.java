package com.byritium.dao;

import com.byritium.entity.transaction.TransactionRefundOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface RefundOrderDao extends PagingAndSortingRepository<TransactionRefundOrder, String> {
    BigDecimal countByPayOrderId(String payOrderId);
}
