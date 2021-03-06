package com.byritium.service.payment;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.transaction.TransactionPayOrder;
import com.byritium.rpc.PaymentRpc;
import com.byritium.service.common.RpcRspService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PaymentService {
    private final PaymentRpc paymentRpc;

    private final RpcRspService<PaymentResult> rpcRspService;

    public PaymentService(PaymentRpc paymentRpc, RpcRspService<PaymentResult> rpcRspService) {
        this.paymentRpc = paymentRpc;
        this.rpcRspService = rpcRspService;
    }

    public PaymentResult pay(List<TransactionPayOrder> transactionPayOrder) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.pay(transactionPayOrder);
        return rpcRspService.get(responseBody);
    }
}
