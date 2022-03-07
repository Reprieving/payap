package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.dao.TransactionPayOrderRepository;
import com.byritium.dto.CouponInfo;
import com.byritium.dto.Deduction;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.rpc.CouponRpc;
import com.byritium.rpc.PaymentPayRpc;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TransactionPayOrderService {

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Resource
    private CouponRpc couponRpc;

    @Resource
    private TransactionPayOrderRepository transactionPayOrderRepository;

    public TransactionPaymentOrder saveOrder(String transactionOrderId, PaymentChannel paymentChannel, BigDecimal amount, String payerId, String mediumId) {
        TransactionPaymentOrder payOrder = new TransactionPaymentOrder();
        payOrder.setTransactionReceiptOrderId(transactionOrderId);
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

        transactionPayOrderRepository.save(payOrder);

        return payOrder;
    }

    public TransactionPaymentOrder saveCoreOrder(String transactionOrderId, PaymentChannel paymentChannel, String payerId, BigDecimal amount) {
        TransactionPaymentOrder payOrder = new TransactionPaymentOrder();
        payOrder.setTransactionReceiptOrderId(transactionOrderId);
        payOrder.setPaymentChannel(paymentChannel);
        payOrder.setPayerId(null);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);

        transactionPayOrderRepository.save(payOrder);

        return null;
    }

    public TransactionPaymentOrder saveCouponOrder(String transactionOrderId, String couponId) {
        PaymentChannel paymentChannel = PaymentChannel.COUPON_PAY;

        ResponseBody<CouponInfo> responseBody = couponRpc.get(couponId);
        CouponInfo couponInfo = responseBody.getData();
        String payerId = couponInfo.getPayerId();
        BigDecimal amount = couponInfo.getAmount();

        TransactionPaymentOrder payOrder = new TransactionPaymentOrder();
        payOrder.setTransactionReceiptOrderId(transactionOrderId);
        payOrder.setPaymentChannel(paymentChannel);
        payOrder.setPayerId(null);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        if (StringUtils.hasText(couponId)) {
            payOrder.setPayMediumId(couponId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        payOrder.setOrderAmount(amount);
        payOrder.setState(PaymentState.PAYMENT_WAITING);


        return transactionPayOrderRepository.save(payOrder);
    }

    public TransactionPaymentOrder saveDeductionOrder(String transactionOrderId, String payerId, Deduction deduction) {
        PaymentChannel paymentChannel = deduction.getPaymentChannel();

        TransactionPaymentOrder payOrder = new TransactionPaymentOrder();
        payOrder.setTransactionReceiptOrderId(transactionOrderId);
        payOrder.setPaymentChannel(paymentChannel);
        payOrder.setPayerId(null);
        if (StringUtils.hasText(payerId)) {
            payOrder.setPayerId(payerId);
        }
        payOrder.setPaymentTitle(paymentChannel.getMessage());
        //TODO calculate deduction amount
        payOrder.setOrderAmount(BigDecimal.ZERO);
        payOrder.setState(PaymentState.PAYMENT_WAITING);


        return transactionPayOrderRepository.save(payOrder);
    }

    public TransactionPaymentOrder saveOrder(TransactionPaymentOrder transactionPaymentOrder) {
        return transactionPayOrderRepository.save(transactionPaymentOrder);
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