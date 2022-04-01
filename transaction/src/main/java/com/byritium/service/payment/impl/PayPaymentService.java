package com.byritium.service.payment.impl;

import com.byritium.constance.PaymentType;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.PayOrder;
import com.byritium.rpc.PaymentPayRpc;
import com.byritium.service.common.RpcRspService;
import com.byritium.service.payment.IPaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayPaymentService extends RpcRspService<PaymentResult> implements IPaymentService {
    @Resource
    private PaymentPayRpc paymentPayRpc;

    @Override
    public PaymentType type() {
        return PaymentType.PAY;
    }

    @Override
    public PaymentResult call(PayOrder payOrder) {
        ResponseBody<PaymentResult> responseBody = paymentPayRpc.pay(payOrder);
        return super.get(responseBody);
    }
}
