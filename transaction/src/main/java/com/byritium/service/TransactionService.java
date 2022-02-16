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
import com.byritium.rpc.PaymentPayRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class TransactionService {

    @Resource
    private TransactionPayOrderService transactionPayOrderService;

    @Resource
    private TransactionOrderRepository transactionOrderRepository;

    @Resource
    private TransactionPayOrderRepository transactionPayOrderRepository;

    @Autowired
    private PaymentPayRpc paymentPayRpc;

    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult result = new TransactionResult();


        PaymentChannel paymentChannel = param.getPaymentChannel();
        TransactionOrder transactionOrder = new TransactionOrder(clientId, param);
        transactionOrderRepository.save(transactionOrder);

        String userId = param.getUserId();
        String transactionOrderId = transactionOrder.getId();

        transactionPayOrderService.buildOrder(transactionOrderId, paymentChannel, BigDecimal.ZERO, null, null);

        String couponId = param.getCouponId();
        transactionPayOrderService.buildOrder(transactionOrderId, paymentChannel, BigDecimal.ZERO, null, couponId);

        Deduction deduction = param.getDeduction();
        transactionPayOrderService.buildOrder(transactionOrderId, deduction.getPaymentChannel(), BigDecimal.ZERO, userId, couponId);



        return result;
    }
}
