package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.dao.TransactionOrderRepository;
import com.byritium.dao.TransactionPayOrderRepository;
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

    @Resource
    private TransactionPayOrderRepository transactionPayOrderRepository;


    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult result = new TransactionResult();


        PaymentChannel paymentChannel = param.getPaymentChannel();
        TransactionOrder transactionOrder = new TransactionOrder(clientId, param);
        transactionOrderRepository.save(transactionOrder);

        String userId = param.getUserId();

        if (paymentChannel != null) {
            TransactionPayOrder corePayOrder = new TransactionPayOrder();
            corePayOrder.setTransactionOrderId(transactionOrder.getId());
            corePayOrder.setPaymentChannel(paymentChannel);
            corePayOrder.setPayerId(null);
            corePayOrder.setPaymentTitle(paymentChannel.getMessage());
            corePayOrder.setOrderAmount(BigDecimal.ZERO);
            corePayOrder.setState(PaymentState.PAYMENT_WAITING);

            transactionPayOrderRepository.save(corePayOrder);
        }

        {
            String couponId = param.getCouponId();
            TransactionPayOrder couponPayOrder = new TransactionPayOrder();
            couponPayOrder.setTransactionOrderId(transactionOrder.getId());
            couponPayOrder.setPaymentChannel(PaymentChannel.COUPON_PAY);
            //TODO coupon payerId
            couponPayOrder.setPayerId(null);
            couponPayOrder.setPayMediumId(couponId);
            couponPayOrder.setPaymentTitle(PaymentChannel.COUPON_PAY.getMessage());
            couponPayOrder.setOrderAmount(BigDecimal.ZERO);
            couponPayOrder.setState(PaymentState.PAYMENT_WAITING);

            transactionPayOrderRepository.save(couponPayOrder);
        }

        {
            //TODO deduction
            Deduction deduction = param.getDeduction();
            TransactionPayOrder deductionPayOrder = new TransactionPayOrder();
            deductionPayOrder.setTransactionOrderId(transactionOrder.getId());
            deductionPayOrder.setPaymentChannel(deduction.getPaymentChannel());
            deductionPayOrder.setPayerId(userId);
            deductionPayOrder.setPaymentTitle(deduction.getPaymentChannel().getMessage());
            deductionPayOrder.setOrderAmount(BigDecimal.ZERO);
            deductionPayOrder.setState(PaymentState.PAYMENT_WAITING);
            transactionPayOrderRepository.save(deductionPayOrder);
        }


        return result;
    }
}
