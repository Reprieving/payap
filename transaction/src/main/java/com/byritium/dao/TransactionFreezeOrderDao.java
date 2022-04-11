package com.byritium.dao;

import com.byritium.entity.FreezeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionFreezeOrderDao extends PagingAndSortingRepository<FreezeOrder, String> {

}
