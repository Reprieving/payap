package com.byritium.service.transaction.order;

import com.byritium.dao.FreeOrderDao;
import com.byritium.entity.transaction.TransactionFreeOrder;
import com.byritium.entity.transaction.TransactionFreezeOrder;
import com.byritium.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class FreeOrderService {

    @Resource
    FreeOrderDao freeOrderDao;


    public TransactionFreeOrder save(TransactionFreeOrder transactionFreeOrder) {
        return freeOrderDao.save(transactionFreeOrder);
    }

    public void verify(TransactionFreezeOrder transactionFreezeOrder, BigDecimal freeAmount) {
        String freezeOrderId = transactionFreezeOrder.getId();
        BigDecimal freezeAmount = transactionFreezeOrder.getOrderAmount();
        BigDecimal frozenAmount = freeOrderDao.countByFreezeOrderId(freezeOrderId);

        BigDecimal surplusRefundAmount = freezeAmount.subtract(frozenAmount);

        if (surplusRefundAmount.compareTo(freeAmount) < 0) {
            throw new BusinessException("解冻余额不足");
        }
    }
}
