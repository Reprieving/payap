package com.byritium.service.payment;

import com.byritium.dao.FreeOrderDao;
import com.byritium.dao.FreezeOrderDao;
import com.byritium.dao.SettleOrderDao;
import com.byritium.entity.FreeOrder;
import com.byritium.entity.FreezeOrder;
import com.byritium.entity.PayOrder;
import com.byritium.entity.SettleOrder;
import com.byritium.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

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
