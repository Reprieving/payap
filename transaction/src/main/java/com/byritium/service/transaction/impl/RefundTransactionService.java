package com.byritium.service.transaction.impl;

import com.byritium.constance.*;
import com.byritium.dto.*;
import com.byritium.entity.transaction.PayOrder;
import com.byritium.entity.transaction.RefundOrder;
import com.byritium.entity.transaction.TradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.transaction.order.RefundOrderService;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.TransactionOrderService;
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

        TradeOrder tradeOrder = transactionOrderService.findByBizOrderId(param.getBusinessOrderId());
        if (tradeOrder == null) {
            throw new BusinessException("未找到交易订单");
        }

        if (param.getOrderAmount().compareTo(tradeOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("退款金额异常");
        }

        PayOrder payOrder = payOrderService.getByTxOrderIdAndPaymentChannel(tradeOrder.getId(), tradeOrder.getPaymentChannel());
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
