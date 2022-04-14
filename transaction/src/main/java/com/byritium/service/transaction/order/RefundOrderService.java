package com.byritium.service.transaction.order;

import com.byritium.dao.RefundOrderDao;
import com.byritium.entity.transaction.TransactionPayOrder;
import com.byritium.entity.transaction.TransactionRefundOrder;
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

    public void verify(TransactionPayOrder transactionPayOrder, BigDecimal refundAmount) {
        String payOrderId = transactionPayOrder.getId();
        BigDecimal payAmount = transactionPayOrder.getOrderAmount();
        BigDecimal refundedAmount = refundOrderDao.countByPayOrderId(payOrderId);

        BigDecimal surplusRefundAmount = payAmount.subtract(refundedAmount);

        if (surplusRefundAmount.compareTo(refundAmount) < 0) {
            throw new BusinessException("退款余额不足");
        }
    }

    public TransactionRefundOrder save(TransactionRefundOrder transactionRefundOrder) {
        return refundOrderDao.save(transactionRefundOrder);
    }
}
