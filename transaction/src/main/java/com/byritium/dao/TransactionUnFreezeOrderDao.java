package com.byritium.dao;

import com.byritium.entity.UnFreezeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionUnFreezeOrderDao extends PagingAndSortingRepository<UnFreezeOrder, String> {

}
