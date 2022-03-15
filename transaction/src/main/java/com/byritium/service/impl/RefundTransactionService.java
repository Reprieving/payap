package com.byritium.service.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionReceiptOrderRepository;
import com.byritium.dao.TransactionPaymentOrderRepository;
import com.byritium.dao.TransactionRefundOrderRepository;
import com.byritium.dto.*;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.entity.TransactionOrder;
import com.byritium.entity.TransactionRefundOrder;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentPayRpc;
import com.byritium.service.ITransactionService;
import com.byritium.service.common.ResponseBodyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class RefundTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.REFUND;
    }

    @Resource
    private TransactionReceiptOrderRepository transactionReceiptOrderRepository;

    @Resource
    private TransactionPaymentOrderRepository transactionPaymentOrderRepository;

    @Resource
    private TransactionRefundOrderRepository transactionRefundOrderRepository;

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private ResponseBodyService<PaymentResult> responseBodyService;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        TransactionOrder transactionOrder = transactionReceiptOrderRepository.findByBusinessOrderId(param.getBusinessOrderId());
        if (transactionOrder == null) {
            throw new BusinessException("未找到订单");
        }

        if (param.getOrderAmount().compareTo(transactionOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("退款金额异常");
        }

        TransactionState transactionState = transactionOrder.getTransactionState();
        String transactionOrderId = transactionOrder.getId();
        PaymentChannel paymentChannel = transactionOrder.getPaymentChannel();
        if (transactionState == TransactionState.TRANSACTION_SUCCESS) {
            TransactionPaymentOrder transactionPaymentOrder = transactionPaymentOrderRepository.findByTransactionOrderIdAndPaymentChannel(transactionOrderId, paymentChannel);
        } else {
            List<TransactionPaymentOrder> paymentOrderList = transactionPaymentOrderRepository.findByTransactionOrderId(transactionOrderId);
        }

        TransactionPaymentOrder transactionPaymentOrder = transactionPaymentOrderRepository.findByTransactionOrderIdAndPaymentChannel(transactionOrderId, paymentChannel);

        String transactionPayOrderId = transactionPaymentOrder.getId();
        String userId = param.getUserId();
        BigDecimal refundAmount = param.getOrderAmount();
        TransactionRefundOrder transactionRefundOrder = new TransactionRefundOrder(userId, transactionPayOrderId, paymentChannel, refundAmount);
        transactionRefundOrderRepository.save(transactionRefundOrder);

        ResponseBody<PaymentResult> responseBody = paymentPayRpc.refund(transactionRefundOrder);
        PaymentResult paymentResult = responseBodyService.get(responseBody);

        PaymentState state = paymentResult.getState();

        if (PaymentState.PAYMENT_SUCCESS == state) {
            //退款入账
            AccountJournal accountJournal = new AccountJournal();
            accountRpc.record(accountJournal);
        }
        transactionResult.setTransactionOrderId(transactionRefundOrder.getId());
        transactionResult.setPaymentState(paymentResult.getState());

        return transactionResult;
    }
}
