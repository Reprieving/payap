package com.byritium.service.transaction.impl;

import com.byritium.constance.*;
import com.byritium.dto.*;
import com.byritium.entity.PayOrder;
import com.byritium.entity.RefundOrder;
import com.byritium.entity.TransactionOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.payment.RefundOrderService;
import com.byritium.service.payment.impl.RefundPaymentService;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.TransactionOrderService;
import com.byritium.service.payment.PayOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

@Service
public class RefundTransactionService implements ITransactionService {
    public RefundTransactionService(TransactionTemplate transactionTemplate, TransactionOrderService transactionOrderService, PayOrderService payOrderService, RefundPaymentService refundPaymentService, RefundOrderService refundOrderService) {
        this.transactionTemplate = transactionTemplate;
        this.transactionOrderService = transactionOrderService;
        this.payOrderService = payOrderService;
        this.refundPaymentService = refundPaymentService;
        this.refundOrderService = refundOrderService;
    }

    private final TransactionTemplate transactionTemplate;
    private final TransactionOrderService transactionOrderService;
    private final PayOrderService payOrderService;
    private final RefundPaymentService refundPaymentService;
    private final RefundOrderService refundOrderService;

    @Override
    public TransactionType type() {
        return TransactionType.REFUND;
    }

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        BigDecimal refundAmount = param.getOrderAmount();

        TransactionResult transactionResult = new TransactionResult();

        TransactionOrder transactionOrder = transactionOrderService.findByBizOrderId(param.getBusinessOrderId());
        if (transactionOrder == null) {
            throw new BusinessException("未找到交易订单");
        }

        if (param.getOrderAmount().compareTo(transactionOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("退款金额异常");
        }

        PayOrder payOrder = payOrderService.getByTxOrderIdAndPaymentChannel(transactionOrder.getId(), transactionOrder.getPaymentChannel());
        if (payOrder == null) {
            throw new BusinessException("未找到支付订单");
        }

        refundOrderService.verify(payOrder, refundAmount);

        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setBizId(payOrder.getBizOrderId());
        refundOrder.setPayerId(payOrder.getId());
        refundOrder.setPaymentChannel(payOrder.getPaymentChannel());
        refundOrder.setPayerId(payOrder.getPayeeId());
        refundOrder.setPayeeId(payOrder.getPayerId());
        refundOrder.setPayMediumId(payOrder.getPayMediumId());
        refundOrder.setOrderAmount(param.getOrderAmount());
        refundOrder.setState(PaymentState.PAYMENT_WAITING);

        refundOrderService.save(refundOrder);

        return transactionResult;
    }
}
