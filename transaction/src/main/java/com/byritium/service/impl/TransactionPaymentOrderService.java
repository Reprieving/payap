package com.byritium.service.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.dao.TransactionPaymentOrderRepository;
import com.byritium.dto.*;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.CouponRpc;
import com.byritium.rpc.PaymentPayRpc;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TransactionPaymentOrderService {

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private CouponRpc couponRpc;

    @Resource
    private TransactionPaymentOrderRepository transactionPaymentOrderRepository;

    public TransactionPaymentOrder save(String transactionOrderId, PaymentChannel paymentChannel, BigDecimal amount, String payerId, String mediumId) {
        TransactionPaymentOrder payOrder = new TransactionPaymentOrder();
        payOrder.setTransactionOrderId(transactionOrderId);
        payOrder.setPaymentChannel(paymentChannel);
        payOrder.setPayerId(null);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        if (StringUtils.hasText(mediumId)) {
            payOrder.setPayMediumId(mediumId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);

        transactionPaymentOrderRepository.save(payOrder);

        return payOrder;
    }

    public TransactionPaymentOrder buildCoreOrder(PaymentChannel paymentChannel, String payerId, BigDecimal amount) {
        TransactionPaymentOrder payOrder = new TransactionPaymentOrder();
        payOrder.setPaymentChannel(paymentChannel);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);

        transactionPaymentOrderRepository.save(payOrder);

        return payOrder;
    }

    public TransactionPaymentOrder buildCoreOrder(String transactionOrderId, PaymentChannel paymentChannel, String payerId, BigDecimal amount) {
        TransactionPaymentOrder payOrder = new TransactionPaymentOrder();
        payOrder.setTransactionOrderId(transactionOrderId);
        payOrder.setPaymentChannel(paymentChannel);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setPaymentAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);

        return payOrder;
    }

    public TransactionPaymentOrder buildCouponOrder(String couponId) {
        PaymentChannel paymentChannel = PaymentChannel.COUPON_PAY;
        ResponseBody<CouponInfo> responseBody = couponRpc.get(couponId);
        CouponInfo couponInfo = responseBody.getData();
        String payerId = couponInfo.getPayerId();
        BigDecimal amount = couponInfo.getAmount();
        TransactionPaymentOrder payOrder = new TransactionPaymentOrder();
        payOrder.setPaymentChannel(paymentChannel);
        payOrder.setPayerId(couponInfo.getPayerId());
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        if (StringUtils.hasText(couponId)) {
            payOrder.setPayMediumId(couponId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setPaymentAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);


        return payOrder;
    }

    public TransactionPaymentOrder buildDeductionOrder(String payerId, Deduction deduction, BigDecimal reductionAmountQuota) {
        PaymentChannel paymentChannel = deduction.getPaymentChannel();
        TransactionPaymentOrder payOrder = new TransactionPaymentOrder();
        payOrder.setPaymentChannel(paymentChannel);
        payOrder.setPayerId(null);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());

        AccountQuery accountQuery = new AccountQuery();
        ResponseBody<AccountBalance> responseBody = accountRpc.query(accountQuery);

        AccountBalance accountBalance = responseBody.getData();
        BigDecimal balanceAmount = accountBalance.getAmount();
        BigDecimal balanceValue = accountBalance.getValue();
        BigDecimal rate = balanceValue.divide(balanceAmount, MathContext.DECIMAL32);

        if (reductionAmountQuota.compareTo(balanceValue) >= 0) {
            payOrder.setOrderAmount(balanceAmount);
            payOrder.setPaymentAmount(balanceValue);
        } else {
            payOrder.setOrderAmount(reductionAmountQuota);
            payOrder.setPaymentAmount(reductionAmountQuota.multiply(rate));
        }

        payOrder.setState(PaymentState.PAYMENT_WAITING);


        return payOrder;
    }

    public TransactionPaymentOrder save(TransactionPaymentOrder transactionPaymentOrder) {
        return transactionPaymentOrderRepository.save(transactionPaymentOrder);
    }

    public CompletableFuture<TransactionPaymentOrder> payOrder(TransactionPaymentOrder transactionPaymentOrder) {
        return CompletableFuture.supplyAsync(() -> {
            ResponseBody<PaymentResult> response = paymentPayRpc.pay(transactionPaymentOrder);
            PaymentResult result = response.getData();
            if (response.success()) {
                transactionPaymentOrder.setState(result.getState());
                transactionPaymentOrder.setSign(result.getSign());
            } else {
                transactionPaymentOrder.setState(PaymentState.PAYMENT_FAIL);
            }
            return transactionPaymentOrder;
        });
    }

    public boolean verifyAllSuccess(List<TransactionPaymentOrder> list) {
        return list.stream().filter(transactionPayOrder -> transactionPayOrder.getState() == PaymentState.PAYMENT_SUCCESS).count() == list.size();
    }
}