package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.dao.TransactionOrderRepository;
import com.byritium.dto.Deduction;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPayOrder;
import com.byritium.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class TransactionService {

    @Resource
    private TransactionOrderRepository transactionOrderRepository;

    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionOrder transactionOrder = new TransactionOrder(clientId, param);
        transactionOrderRepository.save(transactionOrder);

        String userId = param.getUserId();

        //TODO coupon
        String couponId = param.getCouponId();
        TransactionPayOrder couponPayOrder = new TransactionPayOrder();
        couponPayOrder.setTransactionOrderId(transactionOrder.getId());
        couponPayOrder.setPaymentChannel(PaymentChannel.COUPON_PAY);
        couponPayOrder.setPayMediumId(couponId);
        couponPayOrder.setPaymentTitle(PaymentChannel.COUPON_PAY.getMessage());
        couponPayOrder.setOrderAmount(BigDecimal.ZERO);
        couponPayOrder.setState(PaymentState.PAYMENT_WAITING);

        //TODO deduction
        Deduction deduction = param.getDeduction();
        TransactionPayOrder deductionPayOrder = new TransactionPayOrder();
        deductionPayOrder.setTransactionOrderId(transactionOrder.getId());
        couponPayOrder.setPaymentChannel(deduction.getPaymentChannel());
        couponPayOrder.setPaymentTitle(deduction.getPaymentChannel().getMessage());
        couponPayOrder.setOrderAmount(BigDecimal.ZERO);
        couponPayOrder.setState(PaymentState.PAYMENT_WAITING);




        return null;
    }
}
