package com.byritium.service.payment;

import com.byritium.dao.FreezeOrderDao;
import com.byritium.entity.FreezeOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class FreezeOrderService {

    @Resource
    FreezeOrderDao freezeOrderDao;

    public FreezeOrder save(FreezeOrder freezeOrder) {
        return freezeOrderDao.save(freezeOrder);
    }

    public FreezeOrder getByBizOrderId(String businessOrderId) {
        return freezeOrderDao.findByBizOrderId(businessOrderId);
    }
}
