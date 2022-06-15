package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dto.Deduction;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.transaction.TransactionPayOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.rpc.PaymentRpc;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.order.TransactionOrderService;
import com.byritium.service.transaction.order.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GuaranteeTransactionService implements ITransactionService {
    public GuaranteeTransactionService(TransactionOrderService transactionOrderService, PayOrderService payOrderService, PaymentRpc paymentRpc) {
        this.transactionOrderService = transactionOrderService;
        this.payOrderService = payOrderService;
        this.paymentRpc = paymentRpc;
    }

    @Override
    public TransactionType type() {
        return TransactionType.GUARANTEE;
    }

    private final TransactionOrderService transactionOrderService;
    private final PayOrderService payOrderService;
    private final PaymentRpc paymentRpc;

    public TransactionResult call(String clientId, TransactionParam param) {
//        TransactionResult transactionResult = transactionOrderService.trade(clientId, param);
//        return transactionResult;
        return null;
    }

    public TransactionResult call(TransactionParam param) {
        PaymentChannel paymentChannel = param.getPaymentChannel();
        Map<PaymentChannel, TransactionPayOrder> map = new HashMap<>();
        String userId = param.getUserId();

        BigDecimal orderAmount = param.getOrderAmount();

        String couponId = param.getCouponId();
        if (StringUtils.hasText(couponId)) {
            TransactionPayOrder transactionPayOrder = payOrderService.buildCouponOrder(couponId);
            map.put(PaymentChannel.COUPON_PAY, transactionPayOrder);
        }

        Deduction deduction = param.getDeduction();
        if (deduction != null) {
            TransactionPayOrder transactionPayOrder = payOrderService.buildDeductionOrder(userId, deduction);
            map.put(deduction.getPaymentChannel(), transactionPayOrder);
        }

        if (paymentChannel != null) {
            map.put(paymentChannel, payOrderService.buildCoreOrder(paymentChannel, userId, orderAmount));
        }

        TransactionTradeOrder transactionTradeOrder = new TransactionTradeOrder(param);
        transactionOrderService.save(transactionTradeOrder);
        String transactionOrderId = transactionTradeOrder.getId();

        for (Map.Entry<PaymentChannel, TransactionPayOrder> entry : map.entrySet()) {
            TransactionPayOrder transactionPayOrder = entry.getValue();
            transactionPayOrder.setTransactionOrderId(transactionOrderId);
            payOrderService.save(transactionPayOrder);
        }
        return null;
    }

}
