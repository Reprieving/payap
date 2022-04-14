package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dto.*;
import com.byritium.entity.transaction.PayOrder;
import com.byritium.entity.transaction.SettleOrder;
import com.byritium.entity.transaction.TradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.transaction.order.PayOrderService;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.transaction.order.SettleOrderService;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.TransactionOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SettleTransactionService implements ITransactionService {
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
        TradeOrder tradeOrder = transactionOrderService.findByBizOrderId(param.getBusinessOrderId());
        if (tradeOrder == null) {
            throw new BusinessException("未找到交易订单");
        }

        if (param.getOrderAmount().compareTo(tradeOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("分账金额异常");
        }

        PayOrder payOrder = payOrderService.getByTxOrderIdAndPaymentChannel(tradeOrder.getId(), tradeOrder.getPaymentChannel());
        if (payOrder == null) {
            throw new BusinessException("未找到支付订单");
        }
        settleOrderService.verify(payOrder, settleAmount);

        SettleOrder settleOrder = new SettleOrder();
        settleOrder.setBizId(payOrder.getBizOrderId());
        settleOrder.setPayerId(payOrder.getId());
        settleOrder.setPaymentChannel(payOrder.getPaymentChannel());
        settleOrder.setPayerId(payOrder.getPayeeId());
        settleOrder.setPayeeId(payOrder.getPayerId());
        settleOrder.setPayMediumId(payOrder.getPayMediumId());
        settleOrder.setOrderAmount(param.getOrderAmount());
        settleOrder.setState(PaymentState.PAYMENT_WAITING);

        settleOrderService.save(settleOrder);


        return transactionResult;
    }
}
