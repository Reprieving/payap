package com.byritium.dao;

import com.byritium.entity.TransactionTransferOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTransferOrderDao extends PagingAndSortingRepository<TransactionTransferOrder, String> {

}
