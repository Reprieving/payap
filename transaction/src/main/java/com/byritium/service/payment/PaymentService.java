package com.byritium.service.payment;

import com.byritium.dto.PaymentRequest;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.payment.PaymentRefundOrder;
import com.byritium.entity.payment.PaymentTransferOrder;
import com.byritium.entity.payment.PaymentWithdrawOrder;
import com.byritium.rpc.PaymentRpc;
import com.byritium.service.common.RpcRspService;
import com.byritium.entity.payment.PaymentRechargeOrder;
import lombok.extern.slf4j.Slf4j;
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

    public PaymentResult call(PaymentRequest paymentRequest) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.call(paymentRequest);
        return rpcRspService.get(responseBody);
    }
}
