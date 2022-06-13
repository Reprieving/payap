package com.byritium.service.transaction.impl;

import com.byritium.constance.*;
import com.byritium.dto.*;
import com.byritium.entity.transaction.TransactionPayOrder;
import com.byritium.entity.transaction.TransactionRefundOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.transaction.order.RefundOrderService;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.order.TransactionOrderService;
import com.byritium.service.transaction.order.PayOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

@Service
public class RefundTransactionService implements ITransactionService {
    public RefundTransactionService(TransactionTemplate transactionTemplate, TransactionOrderService transactionOrderService, PayOrderService payOrderService, PaymentService paymentService, RefundOrderService refundOrderService) {
        this.transactionTemplate = transactionTemplate;
        this.transactionOrderService = transactionOrderService;
        this.payOrderService = payOrderService;
        this.paymentService = paymentService;
        this.refundOrderService = refundOrderService;
    }

    private final TransactionTemplate transactionTemplate;
    private final TransactionOrderService transactionOrderService;
    private final PayOrderService payOrderService;
    private final PaymentService paymentService;
    private final RefundOrderService refundOrderService;

    @Override
    public TransactionType type() {
        return TransactionType.REFUND;
    }

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        BigDecimal refundAmount = param.getOrderAmount();

        TransactionResult transactionResult = new TransactionResult();

        TransactionTradeOrder transactionTradeOrder = transactionOrderService.findByBizOrderId(param.getBusinessOrderId());
        if (transactionTradeOrder == null) {
            throw new BusinessException("未找到交易订单");
        }

        if (param.getOrderAmount().compareTo(transactionTradeOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("退款金额异常");
        }

        TransactionPayOrder transactionPayOrder = payOrderService.getByTxOrderIdAndPaymentChannel(transactionTradeOrder.getId(), transactionTradeOrder.getPaymentChannel());
        if (transactionPayOrder == null) {
            throw new BusinessException("未找到支付订单");
        }

        refundOrderService.verify(transactionPayOrder, refundAmount);

        TransactionRefundOrder transactionRefundOrder = new TransactionRefundOrder();
        transactionRefundOrder.setBizId(transactionPayOrder.getBizOrderId());
        transactionRefundOrder.setPayerId(transactionPayOrder.getId());
        transactionRefundOrder.setPaymentChannel(transactionPayOrder.getPaymentChannel());
        transactionRefundOrder.setPayerId(transactionPayOrder.getPayeeId());
        transactionRefundOrder.setPayeeId(transactionPayOrder.getPayerId());
        transactionRefundOrder.setPayMediumId(transactionPayOrder.getPayMediumId());
        transactionRefundOrder.setOrderAmount(param.getOrderAmount());
        transactionRefundOrder.setState(PaymentState.PAYMENT_WAITING);

        refundOrderService.save(transactionRefundOrder);

        return transactionResult;
    }
}
