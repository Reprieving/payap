package com.byritium.service.payment.impl;

import com.byritium.constance.PaymentType;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.rpc.PaymentPayRpc;
import com.byritium.service.common.RpcRspService;
import com.byritium.service.payment.IPaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RefundPaymentService extends RpcRspService<PaymentResult> implements IPaymentService {

    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Override
    public PaymentType type() {
        return PaymentType.REFUND;
    }

    @Override
    public PaymentResult call(TransactionPaymentOrder transactionPaymentOrder) {
        ResponseBody<PaymentResult> responseBody = paymentPayRpc.refund(transactionPaymentOrder);
        return super.get(responseBody);
    }
}