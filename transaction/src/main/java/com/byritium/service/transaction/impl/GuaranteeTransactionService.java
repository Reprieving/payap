package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionConst;
import com.byritium.constance.TransactionType;
import com.byritium.dto.Deduction;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.GuaranteeTxReq;
import com.byritium.entity.transaction.TransactionPayOrder;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.TransactionOrderService;
import com.byritium.service.transaction.order.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GuaranteeTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.GUARANTEE;
    }

    @Autowired
    private TransactionOrderService transactionOrderService;

    @Autowired
    private PayOrderService payOrderService;

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

        return null;
    }

}
