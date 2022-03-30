package com.byritium.service.transaction.impl;

import com.byritium.constance.*;
import com.byritium.dto.*;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.entity.TransactionOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.payment.impl.RefundPaymentService;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.TransactionOrderService;
import com.byritium.service.payment.PaymentOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class RefundTransactionService implements ITransactionService {
    public RefundTransactionService(TransactionTemplate transactionTemplate, TransactionOrderService transactionOrderService, PaymentOrderService paymentOrderService, RefundPaymentService refundPaymentService) {
        this.transactionTemplate = transactionTemplate;
        this.transactionOrderService = transactionOrderService;
        this.paymentOrderService = paymentOrderService;
        this.refundPaymentService = refundPaymentService;
    }

    private final TransactionTemplate transactionTemplate;
    private final TransactionOrderService transactionOrderService;
    private final PaymentOrderService paymentOrderService;
    private final RefundPaymentService refundPaymentService;

    @Override
    public TransactionType type() {
        return TransactionType.REFUND;
    }

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult;

        TransactionOrder transactionOrder = transactionOrderService.findByBizOrderId(param.getBusinessOrderId());
        if (transactionOrder == null) {
            throw new BusinessException("未找到订单");
        }

        if (param.getOrderAmount().compareTo(transactionOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("退款金额异常");
        }

        TransactionState transactionState = transactionOrder.getTransactionState();
        String transactionOrderId = transactionOrder.getId();
        PaymentChannel paymentChannel = transactionOrder.getPaymentChannel();
        List<TransactionPaymentOrder> transactionPaymentOrderList = new ArrayList<>(10);
        if (transactionState == TransactionState.TRANSACTION_SUCCESS) {
            TransactionPaymentOrder transactionPaymentOrder = paymentOrderService.getByTxOrderIdAndPaymentChannel(transactionOrderId, paymentChannel);
            transactionPaymentOrderList.add(transactionPaymentOrder);
        } else {
            List<TransactionPaymentOrder> paymentOrderList = paymentOrderService.listByTxOrderId(transactionOrderId);
            transactionPaymentOrderList.addAll(paymentOrderList);
        }

        TransactionOrder transactionRefundOrder = new TransactionOrder();
        BeanUtils.copyProperties(transactionOrder, transactionRefundOrder);
        transactionRefundOrder.setTransactionType(type());
        transactionRefundOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
        transactionRefundOrder.setPaymentState(PaymentState.PAYMENT_PENDING);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                transactionOrderService.save(transactionRefundOrder);
                paymentOrderService.saveAll(transactionPaymentOrderList);
            }
        });

        List<CompletableFuture<TransactionPaymentOrder>> futureList = transactionPaymentOrderList.stream().map(
                        (TransactionPaymentOrder order) -> CompletableFuture.supplyAsync(() -> {
                            PaymentResult paymentResult = refundPaymentService.call(order);
                            order.setState(paymentResult.getState());
                            order.setSign(paymentResult.getSign());
                            return order;
                        }))
                .collect(Collectors.toList());
        transactionResult = paymentOrderService.executePayment(transactionOrder, futureList);

        return transactionResult;
    }
}
