package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionReceiptOrderRepository;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.Deduction;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionReceiptOrder;
import com.byritium.entity.TransactionPayOrder;
import com.byritium.rpc.AccountRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GuaranteeTransactionService implements ITransactionService {

    @Autowired
    private TransactionPayOrderService transactionPayOrderService;

    @Resource
    private TransactionReceiptOrderRepository transactionReceiptOrderRepository;

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
        TransactionReceiptOrder transactionReceiptOrder = new TransactionReceiptOrder(clientId, param);

        List<TransactionPayOrder> transactionOrderList = transactionTemplate.execute(transactionStatus -> {
            List<TransactionPayOrder> list = new ArrayList<>();

            transactionReceiptOrderRepository.save(transactionReceiptOrder);

            String userId = param.getUserId();
            String transactionOrderId = transactionReceiptOrder.getId();

            if (paymentChannel != null) {
                list.add(transactionPayOrderService.saveCoreOrder(transactionOrderId, paymentChannel, userId, BigDecimal.ZERO));
            }

            String couponId = param.getCouponId();
            if (StringUtils.hasText(couponId)) {
                list.add(transactionPayOrderService.saveCouponOrder(transactionOrderId, couponId));
            }

            Deduction deduction = param.getDeduction();
            if (deduction != null) {
                list.add(transactionPayOrderService.saveDeductionOrder(transactionOrderId, userId, deduction));
            }

            return list;
        });

        List<CompletableFuture<TransactionPayOrder>> transactionFutureList = transactionOrderList.stream().map(transactionPayOrderService::payOrder).collect(Collectors.toList());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(transactionFutureList.toArray(new CompletableFuture[0]));

        CompletableFuture<List<TransactionPayOrder>> futureResult = allFutures.thenApply(v -> transactionFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        try {
            List<TransactionPayOrder> transactionPayOrders = futureResult.get();
            transactionPayOrders.forEach(transactionPayOrderService::saveOrder);

            transactionResult.setResult(transactionPayOrders.stream().collect(Collectors.toMap(TransactionPayOrder::getPaymentChannel, TransactionPayOrder -> TransactionPayOrder)));

            if (paymentChannel != null && transactionPayOrderService.verifyAllSuccess(transactionPayOrders)) {
                transactionResult.setPaymentState(PaymentState.PAYMENT_SUCCESS);
                transactionReceiptOrder.setPaymentState(PaymentState.PAYMENT_SUCCESS);
                transactionReceiptOrderRepository.save(transactionReceiptOrder);

                //支付入账
                AccountJournal accountJournal = new AccountJournal();
                accountRpc.record(accountJournal);
            }


        } catch (InterruptedException | ExecutionException e) {
            log.error("get payment order exception", e);
        }

        return transactionResult;
    }

}
