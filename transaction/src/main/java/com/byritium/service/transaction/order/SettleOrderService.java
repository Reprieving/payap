package com.byritium.service.transaction.order;

import com.byritium.dao.SettleOrderDao;
import com.byritium.entity.transaction.PayOrder;
import com.byritium.entity.transaction.SettleOrder;
import com.byritium.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class SettleOrderService {

    @Resource
    SettleOrderDao settleOrderDao;

    public void verify(PayOrder payOrder, BigDecimal refundAmount) {
        String payOrderId = payOrder.getId();
        BigDecimal payAmount = payOrder.getOrderAmount();
        BigDecimal refundedAmount = settleOrderDao.countByPayOrderId(payOrderId);

        BigDecimal surplusRefundAmount = payAmount.subtract(refundedAmount);

        if (surplusRefundAmount.compareTo(refundAmount) < 0) {
            throw new BusinessException("分账余额不足");
        }
    }

    public SettleOrder save(SettleOrder settleOrder) {
        return settleOrderDao.save(settleOrder);
    }
}
