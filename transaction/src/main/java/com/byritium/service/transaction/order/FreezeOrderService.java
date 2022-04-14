package com.byritium.service.transaction.order;

import com.byritium.dao.FreezeOrderDao;
import com.byritium.entity.transaction.TransactionFreezeOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class FreezeOrderService {

    @Resource
    FreezeOrderDao freezeOrderDao;

    public TransactionFreezeOrder save(TransactionFreezeOrder transactionFreezeOrder) {
        return freezeOrderDao.save(transactionFreezeOrder);
    }

    public TransactionFreezeOrder getByBizOrderId(String businessOrderId) {
        return freezeOrderDao.findByBizOrderId(businessOrderId);
    }
}
