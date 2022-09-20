package com.byritium.dao;

import com.byritium.service.callback.entity.PayOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PayOrderDao extends PagingAndSortingRepository<PayOrder, String> {

}
