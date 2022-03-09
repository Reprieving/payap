package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionPayOrderRepository;
import com.byritium.dao.TransactionReceiptOrderRepository;
import com.byritium.dto.Deduction;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionReceiptOrder;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.rpc.AccountRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GuaranteeTransactionService implements ITransactionService {

    @Autowired
    private TransactionPayOrderService transactionPayOrderService;

    @Resource
    private TransactionReceiptOrderRepository transactionReceiptOrderRepository;

    @Resource
    private TransactionPayOrderRepository transactionPayOrderRepository;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private AccountRpc accountRpc;

    @Override
    public TransactionType type() {
        return TransactionType.GUARANTEE;
    }

    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        PaymentChannel paymentChannel = param.getPaymentChannel();
        Map<PaymentChannel, TransactionPaymentOrder> map = new HashMap<>();
        String userId = param.getUserId();

        BigDecimal reductionAmount = BigDecimal.ZERO;

        String couponId = param.getCouponId();
        if (StringUtils.hasText(couponId)) {
            TransactionPaymentOrder payOrder = transactionPayOrderService.buildCouponOrder(couponId);
            map.put(PaymentChannel.COUPON_PAY, payOrder);
            reductionAmount = reductionAmount.add(payOrder.getOrderAmount());
        }

        Deduction deduction = param.getDeduction();
        if (deduction != null) {
            TransactionPaymentOrder payOrder = transactionPayOrderService.buildDeductionOrder(userId, deduction);
            map.put(deduction.getPaymentChannel(), payOrder);
            reductionAmount = reductionAmount.add(payOrder.getPaymentAmount());
        }


        BigDecimal corePaymentOrderAmount = param.getOrderAmount().subtract(reductionAmount);
        corePaymentOrderAmount = corePaymentOrderAmount.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : corePaymentOrderAmount;

        map.put(paymentChannel, transactionPayOrderService.buildCoreOrder(paymentChannel, userId, corePaymentOrderAmount));


        TransactionReceiptOrder transactionReceiptOrder = new TransactionReceiptOrder(clientId, param);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                transactionReceiptOrderRepository.save(transactionReceiptOrder);
                String transactionOrderId = transactionReceiptOrder.getId();

                for (Map.Entry<PaymentChannel, TransactionPaymentOrder> entry : map.entrySet()) {
                    TransactionPaymentOrder transactionPaymentOrder = entry.getValue();
                    transactionPaymentOrder.setTransactionOrderId(transactionOrderId);
                    transactionPayOrderRepository.save(transactionPaymentOrder);
                }
            }
        });


//        List<CompletableFuture<TransactionPaymentOrder>> transactionFutureList = transactionOrderList.stream().map(transactionPayOrderService::payOrder).collect(Collectors.toList());
//
//        CompletableFuture<Void> allFutures = CompletableFuture.allOf(transactionFutureList.toArray(new CompletableFuture[0]));
//
//        CompletableFuture<List<TransactionPaymentOrder>> futureResult = allFutures.thenApply(v -> transactionFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
//
//        try {
//            List<TransactionPaymentOrder> transactionPaymentOrders = futureResult.get();
//            transactionPaymentOrders.forEach(transactionPayOrderService::saveOrder);
//
//            transactionResult.setResult(transactionPaymentOrders.stream().collect(Collectors.toMap(TransactionPaymentOrder::getPaymentChannel, TransactionPayOrder -> TransactionPayOrder)));
//
//            if (paymentChannel != null && transactionPayOrderService.verifyAllSuccess(transactionPaymentOrders)) {
//                transactionResult.setPaymentState(PaymentState.PAYMENT_SUCCESS);
//                transactionReceiptOrder.setPaymentState(PaymentState.PAYMENT_SUCCESS);
//                transactionReceiptOrderRepository.save(transactionReceiptOrder);
//
//                //支付入账
//                AccountJournal accountJournal = new AccountJournal();
//                accountRpc.record(accountJournal);
//            }
//
//
//        } catch (InterruptedException | ExecutionException e) {
//            log.error("get payment order exception", e);
//        }

        return transactionResult;
    }

}
