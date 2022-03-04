package com.byritium.dao;

import com.byritium.entity.TransactionSettleOrder;
import com.byritium.entity.TransactionTransferOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTransferOrderRepository extends PagingAndSortingRepository<TransactionTransferOrder, String> {

}
