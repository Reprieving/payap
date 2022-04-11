package com.byritium.service.payment;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.PayOrder;
import com.byritium.entity.RefundOrder;
import com.byritium.entity.SettleOrder;
import com.byritium.entity.TransferOrder;
import com.byritium.rpc.PaymentRpc;
import com.byritium.service.common.RpcRspService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService {
    private final PaymentRpc paymentRpc;

    private final RpcRspService<PaymentResult> rpcRspService;

    public PaymentService(PaymentRpc paymentRpc, RpcRspService<PaymentResult> rpcRspService) {
        this.paymentRpc = paymentRpc;
        this.rpcRspService = rpcRspService;
    }


    public PaymentResult pay(PayOrder payOrder) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.pay(payOrder);
        PaymentResult paymentResult = rpcRspService.get(responseBody);

        return paymentResult;
    }

    public PaymentResult refund(RefundOrder refundOrder) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.refund(refundOrder);
        PaymentResult paymentResult = rpcRspService.get(responseBody);

        return paymentResult;
    }

    public PaymentResult settle(SettleOrder settleOrder) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.settle(settleOrder);
        PaymentResult paymentResult = rpcRspService.get(responseBody);

        return paymentResult;
    }

    public PaymentResult transfer(TransferOrder transferOrder) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.transfer(transferOrder);
        PaymentResult paymentResult = rpcRspService.get(responseBody);

        return paymentResult;
    }
}
