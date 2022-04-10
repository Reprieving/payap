package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import com.byritium.dto.*;
import com.byritium.entity.PayOrder;
import com.byritium.entity.RefundOrder;
import com.byritium.entity.SettleOrder;
import com.byritium.entity.TransactionOrder;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentRpc;
import com.byritium.service.payment.PayOrderService;
import com.byritium.service.payment.PaymentService;
import com.byritium.service.payment.RefundOrderService;
import com.byritium.service.payment.SettleOrderService;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.common.RpcRspService;
import com.byritium.service.transaction.TransactionOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
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
        TransactionOrder transactionOrder = transactionOrderService.findByBizOrderId(param.getBusinessOrderId());
        if (transactionOrder == null) {
            throw new BusinessException("未找到交易订单");
        }

        if (param.getOrderAmount().compareTo(transactionOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("分账金额异常");
        }

        PayOrder payOrder = payOrderService.getByTxOrderIdAndPaymentChannel(transactionOrder.getId(), transactionOrder.getPaymentChannel());
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
        PaymentResult paymentResult = paymentService.settle(settleOrder);

        return transactionResult;
    }
}
