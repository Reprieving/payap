package com.byritium.dao;

import com.byritium.entity.TransactionSettleOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionSettleOrderDao extends PagingAndSortingRepository<TransactionSettleOrder, String> {

}
