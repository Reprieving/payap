package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.dao.TransactionOrderRepository;
import com.byritium.dto.Deduction;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionPayOrderService transactionPayOrderService;

    @Resource
    private TransactionOrderRepository transactionOrderRepository;

    @Resource
    private TransactionTemplate transactionTemplate;

    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        PaymentChannel paymentChannel = param.getPaymentChannel();
        TransactionOrder transactionOrder = new TransactionOrder(clientId, param);

        List<TransactionPayOrder> transactionOrderList = new ArrayList<>();

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                transactionOrderRepository.save(transactionOrder);

                String userId = param.getUserId();
                String transactionOrderId = transactionOrder.getId();

                {
                    transactionOrderList.add(
                            transactionPayOrderService.saveOrder(transactionOrderId, paymentChannel, BigDecimal.ZERO, null, null)
                    );
                }

                {
                    String couponId = param.getCouponId();
                    transactionOrderList.add(
                            transactionPayOrderService.saveOrder(transactionOrderId, PaymentChannel.COUPON_PAY, BigDecimal.ZERO, null, couponId)
                    );
                }

                {
                    Deduction deduction = param.getDeduction();
                    transactionOrderList.add(
                            transactionPayOrderService.saveOrder(transactionOrderId, deduction.getPaymentChannel(), BigDecimal.ZERO, userId, null)
                    );
                }

            }
        });

        List<CompletableFuture<TransactionPayOrder>> transactionFutureList = transactionOrderList.stream().map(
                transactionPayOrder -> transactionPayOrderService.payOrder(transactionPayOrder)).collect(Collectors.toList()
        );

        CompletableFuture<Void> allFutures =
                CompletableFuture
                        .allOf(transactionFutureList.toArray(new CompletableFuture[transactionFutureList.size()]));

        CompletableFuture<List<TransactionPayOrder>> futureResult = allFutures.thenApply(v -> transactionFutureList.stream().map(CompletableFuture::join)
                .collect(Collectors.toList()));


        try {
            List<TransactionPayOrder> transactionPayOrders = futureResult.get();
            transactionPayOrders.forEach(transactionPayOrder -> transactionPayOrderService.saveOrder(transactionPayOrder));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return transactionResult;
    }
}
