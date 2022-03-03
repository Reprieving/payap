package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionReceiptOrderRepository;
import com.byritium.dao.TransactionPayOrderRepository;
import com.byritium.dao.TransactionRefundOrderRepository;
import com.byritium.dto.*;
import com.byritium.entity.TransactionPayOrder;
import com.byritium.entity.TransactionReceiptOrder;
import com.byritium.entity.TransactionRefundOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentPayRpc;
import com.byritium.utils.ResponseBodyUtils;
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
    private TransactionReceiptOrderRepository transactionReceiptOrderRepository;

    @Resource
    private TransactionPayOrderRepository transactionPayOrderRepository;

    @Resource
    private TransactionRefundOrderRepository transactionRefundOrderRepository;

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Resource
    private AccountRpc accountRpc;

    @Resource
    private ResponseBodyUtils<PaymentResult> resultResponseBodyUtils;

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        TransactionReceiptOrder transactionOrder = transactionReceiptOrderRepository.findByBusinessOrderId(param.getBusinessOrderId());
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

        PaymentState state = paymentResult.getState();

        if(PaymentState.PAYMENT_SUCCESS== state){
            //退款入账
            AccountJournal accountJournal = new AccountJournal();
            accountRpc.record(accountJournal);
        }
        transactionResult.setTransactionOrderId(transactionRefundOrder.getId());
        transactionResult.setPaymentState(paymentResult.getState());

        return transactionResult;
    }
}
