package com.byritium.service.payment;

import com.byritium.dao.RefundOrderDao;
import com.byritium.entity.PayOrder;
import com.byritium.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class RefundOrderService {

    @Resource
    RefundOrderDao refundOrderDao;

    public void verify(PayOrder payOrder, BigDecimal refundAmount) {
        String payOrderId = payOrder.getId();
        BigDecimal payAmount = payOrder.getOrderAmount();
        BigDecimal refundedAmount = refundOrderDao.countByPayOrderId(payOrderId);

        BigDecimal surplusRefundAmount = payAmount.subtract(refundedAmount);

        if (surplusRefundAmount.compareTo(refundAmount) < 0) {
            throw new BusinessException("退款余额不足");
        }
    }
}
