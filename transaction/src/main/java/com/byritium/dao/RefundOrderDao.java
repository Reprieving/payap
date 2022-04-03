package com.byritium.dao;

import com.byritium.constance.PaymentChannel;
import com.byritium.entity.PayOrder;
import com.byritium.entity.RefundOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RefundOrderDao extends PagingAndSortingRepository<RefundOrder, String> {
    BigDecimal countByPayOrderId(String payOrderId);
}
