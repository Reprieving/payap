package com.byritium.service.payment;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.PaymentType;
import com.byritium.dao.PaymentOrderDao;
import com.byritium.dto.*;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.CouponRpc;
import com.byritium.service.transaction.TransactionOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentOrderService implements ApplicationContextAware {
    private static Map<PaymentType, IPaymentService> paymentServiceMap;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private CouponRpc couponRpc;

    @Resource
    private TransactionOrderService transactionOrderService;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        paymentServiceMap = new HashMap<>();
        Map<String, IPaymentService> map = applicationContext.getBeansOfType(IPaymentService.class);
        map.forEach((key, value) -> {
            if (value.type() != null)
                paymentServiceMap.put(value.type(), value);
        });
    }

    public PaymentResult callPayment(PaymentType paymentType, TransactionPaymentOrder transactionPaymentOrder) {
        return paymentServiceMap.get(paymentType).call(transactionPaymentOrder);
    }

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

        paymentOrderDao.save(payOrder);

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

        paymentOrderDao.save(payOrder);

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
        return paymentOrderDao.save(transactionPaymentOrder);
    }

    public TransactionPaymentOrder getByTxOrderIdAndPaymentChannel(String orderId, PaymentChannel paymentChannel) {
        return paymentOrderDao.findByTransactionOrderIdAndPaymentChannel(orderId, paymentChannel);
    }

    public List<TransactionPaymentOrder> listByTxOrderId(String orderId) {
        return paymentOrderDao.findByTransactionOrderId(orderId);
    }

    public Iterable<TransactionPaymentOrder> saveAll(Iterable<TransactionPaymentOrder> iterable) {
        return paymentOrderDao.saveAll(iterable);
    }

    public CompletableFuture<TransactionPaymentOrder> slotPayment(PaymentType paymentType, TransactionPaymentOrder transactionPaymentOrder) {
        return CompletableFuture.supplyAsync(() -> {
            PaymentResult paymentResult = callPayment(paymentType, transactionPaymentOrder);
            transactionPaymentOrder.setState(paymentResult.getState());
            transactionPaymentOrder.setSign(paymentResult.getSign());
            return transactionPaymentOrder;
        });
    }

    public boolean verifyAllSuccess(List<TransactionPaymentOrder> list) {
        return list.stream().filter(transactionPayOrder -> transactionPayOrder.getState() == PaymentState.PAYMENT_SUCCESS).count() == list.size();
    }


    public TransactionResult executePayment(TransactionOrder transactionOrder, List<CompletableFuture<TransactionPaymentOrder>> futureList) {
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        CompletableFuture<List<TransactionPaymentOrder>> futureResult = allFutures.thenApply(v -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        PaymentChannel paymentChannel = transactionOrder.getPaymentChannel();
        return transactionTemplate.execute(transactionStatus -> {
            TransactionResult transactionResult = new TransactionResult();
            List<TransactionPaymentOrder> transactionPaymentOrders;
            try {
                transactionPaymentOrders = futureResult.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("get payment order exception", e);
                throw new BusinessException("get payment order exception");
            }
            paymentOrderDao.saveAll(transactionPaymentOrders);
            transactionResult.setPaymentOrders(transactionPaymentOrders.stream().collect(Collectors.toMap(TransactionPaymentOrder::getPaymentChannel, TransactionPayOrder -> TransactionPayOrder)));

            if (paymentChannel != null && verifyAllSuccess(transactionPaymentOrders)) {
                transactionResult.setPaymentState(PaymentState.PAYMENT_SUCCESS);
                transactionOrder.setPaymentState(PaymentState.PAYMENT_SUCCESS);
                transactionOrderService.save(transactionOrder);
            }
            return transactionResult;
        });
    }


}