package com.byritium.dao;

import com.byritium.entity.TransactionFreezeOrder;
import com.byritium.entity.TransactionTransferOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionFreezeOrderRepository extends PagingAndSortingRepository<TransactionFreezeOrder, String> {

}
