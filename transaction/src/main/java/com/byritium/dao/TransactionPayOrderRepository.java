package com.byritium.dao;

import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPayOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionPayOrderRepository extends PagingAndSortingRepository<TransactionPayOrder, String> {
}
