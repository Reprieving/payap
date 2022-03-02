package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionOrderRepository;
import com.byritium.dao.TransactionPayOrderRepository;
import com.byritium.dao.TransactionRefundOrderRepository;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionPayOrder;
import com.byritium.entity.TransactionRefundOrder;
import com.byritium.rpc.PaymentPayRpc;
import com.byritium.utils.ResponseBodyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class RefundTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.REFUND;
    }

    @Resource
    private TransactionOrderRepository transactionOrderRepository;

    @Resource
    private TransactionPayOrderRepository transactionPayOrderRepository;

    @Resource
    private TransactionRefundOrderRepository transactionRefundOrderRepository;

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Resource
    private ResponseBodyUtils<PaymentResult> resultResponseBodyUtils;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        TransactionOrder transactionOrder = transactionOrderRepository.findByBusinessOrderId(param.getBusinessOrderId());
        String transactionOrderId = transactionOrder.getId();
        PaymentChannel paymentChannel = transactionOrder.getPaymentChannel();
        TransactionPayOrder transactionPayOrder = transactionPayOrderRepository.findByTransactionOrderIdAndPaymentChannel(transactionOrderId, paymentChannel);


        String transactionPayOrderId = transactionPayOrder.getId();
        String userId = param.getUserId();
        BigDecimal refundAmount = param.getOrderAmount();
        TransactionRefundOrder transactionRefundOrder = new TransactionRefundOrder(userId, transactionPayOrderId, paymentChannel, refundAmount);
        transactionRefundOrderRepository.save(transactionRefundOrder);

        ResponseBody<PaymentResult> responseBody = paymentPayRpc.refund(transactionRefundOrder);
        PaymentResult paymentResult = resultResponseBodyUtils.get(responseBody);


        transactionResult.setTransactionOrderId(transactionRefundOrder.getId());
        transactionResult.setPaymentState(paymentResult.getState());

        return transactionResult;
    }
}
