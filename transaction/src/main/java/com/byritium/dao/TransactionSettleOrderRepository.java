package com.byritium.dao;

import com.byritium.entity.TransactionRefundOrder;
import com.byritium.entity.TransactionSettleOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionSettleOrderRepository extends PagingAndSortingRepository<TransactionSettleOrder, String> {

}
