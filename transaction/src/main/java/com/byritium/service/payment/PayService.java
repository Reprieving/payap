package com.byritium.service.payment;

import com.byritium.dto.PaymentResult;
import com.byritium.rpc.PaymentRpc;
import com.byritium.service.common.RpcRspService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayService {
    private final PaymentRpc paymentRpc;

    private final RpcRspService<PaymentResult> rpcRspService;

    public PayService(PaymentRpc paymentRpc, RpcRspService<PaymentResult> rpcRspService) {
        this.paymentRpc = paymentRpc;
        this.rpcRspService = rpcRspService;
    }
}
