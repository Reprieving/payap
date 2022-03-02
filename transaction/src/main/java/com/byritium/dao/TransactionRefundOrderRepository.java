package com.byritium.dao;

import com.byritium.constance.PaymentChannel;
import com.byritium.entity.TransactionPayOrder;
import com.byritium.entity.TransactionRefundOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRefundOrderRepository extends PagingAndSortingRepository<TransactionRefundOrder, String> {

}
