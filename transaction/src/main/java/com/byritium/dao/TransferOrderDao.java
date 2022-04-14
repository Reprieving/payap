package com.byritium.dao;

import com.byritium.entity.transaction.TransferOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferOrderDao extends PagingAndSortingRepository<TransferOrder, String> {



}
