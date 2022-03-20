package com.byritium.dao;

import com.byritium.entity.TransactionFreezeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionFreezeOrderDao extends PagingAndSortingRepository<TransactionFreezeOrder, String> {

}
