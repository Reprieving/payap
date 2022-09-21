package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dto.*;
import com.byritium.entity.transaction.TransactionPayOrder;
import com.byritium.entity.transaction.TransactionSettleOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.transaction.order.PayOrderService;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.order.SettleOrderService;
import com.byritium.service.transaction.ITransactionCallService;
import com.byritium.service.transaction.order.TransactionOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SettleTransactionService implements ITransactionCallService {
    @Override
    public TransactionType type() {
        return TransactionType.SETTLE;
    }

    public SettleTransactionService(TransactionOrderService transactionOrderService, PayOrderService payOrderService, PaymentService paymentService, SettleOrderService settleOrderService) {
        this.transactionOrderService = transactionOrderService;
        this.payOrderService = payOrderService;
        this.paymentService = paymentService;
        this.settleOrderService = settleOrderService;
    }

    private final TransactionOrderService transactionOrderService;
    private final PayOrderService payOrderService;
    private final PaymentService paymentService;
    private final SettleOrderService settleOrderService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        BigDecimal settleAmount = param.getOrderAmount();
        TransactionResult transactionResult = new TransactionResult();
        TransactionTradeOrder transactionTradeOrder = transactionOrderService.findByBizOrderId(param.getBusinessOrderId());
        if (transactionTradeOrder == null) {
            throw new BusinessException("未找到交易订单");
        }

        if (param.getOrderAmount().compareTo(transactionTradeOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("分账金额异常");
        }

        TransactionPayOrder transactionPayOrder = payOrderService.getByTxOrderIdAndPaymentChannel(transactionTradeOrder.getId(), transactionTradeOrder.getPaymentPattern());
        if (transactionPayOrder == null) {
            throw new BusinessException("未找到支付订单");
        }
        settleOrderService.verify(transactionPayOrder, settleAmount);

        TransactionSettleOrder transactionSettleOrder = new TransactionSettleOrder();
        transactionSettleOrder.setBizId(transactionPayOrder.getBizOrderId());
        transactionSettleOrder.setPayerId(transactionPayOrder.getId());
        transactionSettleOrder.setPaymentPattern(transactionPayOrder.getPaymentPattern());
        transactionSettleOrder.setPayerId(transactionPayOrder.getPayeeId());
        transactionSettleOrder.setPayeeId(transactionPayOrder.getPayerId());
        transactionSettleOrder.setPayMediumId(transactionPayOrder.getPayMediumId());
        transactionSettleOrder.setOrderAmount(param.getOrderAmount());
        transactionSettleOrder.setState(PaymentState.PAYMENT_WAITING);

        settleOrderService.save(transactionSettleOrder);


        return transactionResult;
    }
}
