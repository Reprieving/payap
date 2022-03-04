package com.byritium.dao;

import com.byritium.entity.TransactionFreezeOrder;
import com.byritium.entity.TransactionUnFreezeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionUnFreezeOrderRepository extends PagingAndSortingRepository<TransactionUnFreezeOrder, String> {

}
