package com.byritium.dao;

import com.byritium.entity.TransactionRefundOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRefundOrderDao extends PagingAndSortingRepository<TransactionRefundOrder, String> {

}
