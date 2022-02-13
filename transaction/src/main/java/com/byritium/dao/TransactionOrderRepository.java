package com.byritium.dao;

import com.byritium.entity.TransactionOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionOrderRepository extends PagingAndSortingRepository<TransactionOrder, String> {
}
