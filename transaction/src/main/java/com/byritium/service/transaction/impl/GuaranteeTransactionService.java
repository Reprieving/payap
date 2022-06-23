package com.byritium.service.transaction.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dto.*;
import com.byritium.entity.transaction.TransactionPayOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.PaymentRpc;
import com.byritium.service.transaction.ITransactionCallBackService;
import com.byritium.service.transaction.ITransactionCallService;
import com.byritium.service.transaction.order.PayOrderService;
import com.byritium.service.transaction.order.TransactionOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GuaranteeTransactionService implements ITransactionCallService, ITransactionCallBackService {
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
        return null;
    }


    public TransactionResult call(TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();
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

        ResponseBody<PaymentResult> responseBody = paymentRpc.pay(map.get(paymentChannel));
        PaymentResult paymentResult = responseBody.getData();
        transactionResult.setPaySign(paymentResult.getSign());
        return transactionResult;
    }

    @Override
    public TransactionResult paymentCallback(PaymentResult paymentResult) {
        TransactionPayOrder transactionPayOrder = payOrderService.get(paymentResult.getPaymentOrderId());
        if (transactionPayOrder == null) {
            throw new BusinessException("未找到订单");
        }

        PaymentState paymentState = paymentResult.getState();
        transactionPayOrder.setState(paymentState);


        if (PaymentState.PAYMENT_SUCCESS == paymentState && transactionPayOrder.getCoreFlag()) {

        }

        return null;
    }

    @Override
    public TransactionResult refundCallback(PaymentResult paymentResult) {
        return null;
    }
}
