package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionOrderRepository;
import com.byritium.dto.Deduction;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPayOrder;
import lombok.extern.slf4j.Slf4j;
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
    private final TransactionPayOrderService transactionPayOrderService;

    @Resource
    private TransactionOrderRepository transactionOrderRepository;

    @Resource
    private TransactionTemplate transactionTemplate;

    public GuaranteeTransactionService(TransactionPayOrderService transactionPayOrderService) {
        this.transactionPayOrderService = transactionPayOrderService;
    }

    @Override
    public TransactionType type() {
        return TransactionType.GUARANTEE;
    }

    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        PaymentChannel paymentChannel = param.getPaymentChannel();
        TransactionOrder transactionOrder = new TransactionOrder(clientId, param);

        List<TransactionPayOrder> transactionOrderList = transactionTemplate.execute(transactionStatus -> {
            List<TransactionPayOrder> transactionOrderList1 = new ArrayList<>();

            transactionOrderRepository.save(transactionOrder);

            String userId = param.getUserId();
            String transactionOrderId = transactionOrder.getId();

            if (paymentChannel != null) {
                transactionOrderList1.add(
                        transactionPayOrderService.saveCoreOrder(transactionOrderId, paymentChannel, userId, BigDecimal.ZERO)
                );
            }

            String couponId = param.getCouponId();
            if (StringUtils.hasText(couponId)) {
                transactionOrderList1.add(
                        transactionPayOrderService.saveCouponOrder(transactionOrderId, couponId)
                );
            }

            Deduction deduction = param.getDeduction();
            if (deduction != null) {
                transactionOrderList1.add(
                        transactionPayOrderService.saveDeductionOrder(transactionOrderId, userId, deduction)
                );
            }

            return transactionOrderList1;
        });

        List<CompletableFuture<TransactionPayOrder>> transactionFutureList = transactionOrderList.stream().map(
                transactionPayOrderService::payOrder).collect(Collectors.toList()
        );

        CompletableFuture<Void> allFutures =
                CompletableFuture
                        .allOf(transactionFutureList.toArray(new CompletableFuture[0]));


        CompletableFuture<List<TransactionPayOrder>> futureResult = allFutures.thenApply(v -> transactionFutureList.stream().map(CompletableFuture::join)
                .collect(Collectors.toList()));

        try {
            List<TransactionPayOrder> transactionPayOrders = futureResult.get();
            transactionPayOrders.forEach(transactionPayOrderService::saveOrder);

            //支付入账
            if (paymentChannel != null && transactionPayOrderService.verifyAllSuccess(transactionPayOrders)) {

            }


        } catch (InterruptedException | ExecutionException e) {
            log.error("get payment order exception", e);
        }

        return transactionResult;
    }

}
