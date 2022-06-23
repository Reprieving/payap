package com.byritium.service.transaction.order;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.dao.PayOrderDao;
import com.byritium.dto.*;
import com.byritium.entity.transaction.TransactionPayOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.CouponRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PayOrderService {

    private final AccountRpc accountRpc;
    private final CouponRpc couponRpc;
    private final TransactionOrderService transactionOrderService;
    private final PayOrderDao payOrderDao;
    private final TransactionTemplate transactionTemplate;

    public PayOrderService(AccountRpc accountRpc, CouponRpc couponRpc, TransactionOrderService transactionOrderService, PayOrderDao payOrderDao, TransactionTemplate transactionTemplate) {
        this.accountRpc = accountRpc;
        this.couponRpc = couponRpc;
        this.transactionOrderService = transactionOrderService;
        this.payOrderDao = payOrderDao;
        this.transactionTemplate = transactionTemplate;
    }

    public TransactionPayOrder get(String id) {
        return payOrderDao.findById(id).orElse(null);
    }

    public TransactionPayOrder update(TransactionPayOrder transactionPayOrder) {
        return payOrderDao.save(transactionPayOrder);
    }

    public TransactionPayOrder buildCoreOrder(PaymentChannel paymentChannel, String payerId, BigDecimal amount) {
        TransactionPayOrder transactionPayOrder = new TransactionPayOrder();
        transactionPayOrder.setPaymentChannel(paymentChannel);
        if (StringUtils.hasText(payerId)) {
            transactionPayOrder.setPayerId(payerId);
        }
        transactionPayOrder.setPaymentTitle(paymentChannel.getMessage());
        transactionPayOrder.setOrderAmount(amount);
        transactionPayOrder.setState(PaymentState.PAYMENT_WAITING);

        payOrderDao.save(transactionPayOrder);
        return transactionPayOrder;
    }

    public TransactionPayOrder buildCouponOrder(String couponId) {
        PaymentChannel paymentChannel = PaymentChannel.COUPON_PAY;
        ResponseBody<CouponInfo> responseBody = couponRpc.get(couponId);
        CouponInfo couponInfo = responseBody.getData();
        String payerId = couponInfo.getPayerId();
        BigDecimal amount = couponInfo.getAmount();
        TransactionPayOrder transactionPayOrder = new TransactionPayOrder();
        transactionPayOrder.setPaymentChannel(paymentChannel);
        transactionPayOrder.setPayerId(couponInfo.getPayerId());
        if (StringUtils.hasText(payerId)) {
            transactionPayOrder.setPayerId(payerId);
        }
        if (StringUtils.hasText(couponId)) {
            transactionPayOrder.setPayMediumId(couponId);
        }
        transactionPayOrder.setPaymentTitle(paymentChannel.getMessage());
        transactionPayOrder.setOrderAmount(amount);
        transactionPayOrder.setPaymentAmount(amount);
        transactionPayOrder.setState(PaymentState.PAYMENT_WAITING);


        return transactionPayOrder;
    }

    public TransactionPayOrder buildDeductionOrder(String payerId, Deduction deduction) {
        PaymentChannel paymentChannel = deduction.getPaymentChannel();
        TransactionPayOrder transactionPayOrder = new TransactionPayOrder();
        transactionPayOrder.setPaymentChannel(paymentChannel);
        transactionPayOrder.setPayerId(null);
        if (StringUtils.hasText(payerId)) {
            transactionPayOrder.setPayerId(payerId);
        }
        transactionPayOrder.setPaymentTitle(paymentChannel.getMessage());

        AccountQuery accountQuery = new AccountQuery();
        ResponseBody<AccountBalance> responseBody = accountRpc.query(accountQuery);

        AccountBalance accountBalance = responseBody.getData();
        BigDecimal balanceAmount = accountBalance.getAmount();
        BigDecimal balanceValue = accountBalance.getValue();
        BigDecimal rate = balanceValue.divide(balanceAmount, MathContext.DECIMAL32);

        transactionPayOrder.setState(PaymentState.PAYMENT_WAITING);


        return transactionPayOrder;
    }

    public TransactionPayOrder save(TransactionPayOrder transactionPayOrder) {
        return payOrderDao.save(transactionPayOrder);
    }

    public TransactionPayOrder getByTxOrderIdAndPaymentChannel(String orderId, PaymentChannel paymentChannel) {
        return payOrderDao.findByTransactionOrderIdAndPaymentChannel(orderId, paymentChannel);
    }

    public boolean verifyAllSuccess(List<TransactionPayOrder> list) {
        return list.stream().filter(transactionPayOrder -> transactionPayOrder.getState() == PaymentState.PAYMENT_SUCCESS).count() == list.size();
    }


    public TransactionResult executePayment(TransactionTradeOrder transactionTradeOrder, List<CompletableFuture<TransactionPayOrder>> futureList) {
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        CompletableFuture<List<TransactionPayOrder>> futureResult = allFutures.thenApply(v -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        PaymentChannel paymentChannel = transactionTradeOrder.getPaymentChannel();
        return transactionTemplate.execute(transactionStatus -> {
            TransactionResult transactionResult = new TransactionResult();
            List<TransactionPayOrder> transactionPayOrders;
            try {
                transactionPayOrders = futureResult.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("get payment order exception", e);
                throw new BusinessException("get payment order exception");
            }
            payOrderDao.saveAll(transactionPayOrders);

            if (paymentChannel != null && verifyAllSuccess(transactionPayOrders)) {
                transactionResult.setPaymentState(PaymentState.PAYMENT_SUCCESS);
                transactionTradeOrder.setPaymentState(PaymentState.PAYMENT_SUCCESS);
                transactionOrderService.save(transactionTradeOrder);
            }
            return transactionResult;
        });
    }


    public List<TransactionPayOrder> listByNotCoreFlag(String transactionId) {
        return null;
    }
}