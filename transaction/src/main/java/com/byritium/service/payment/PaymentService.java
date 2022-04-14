package com.byritium.service.payment;

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


    public PaymentResult recharge(PaymentRechargeOrder paymentRechargeOrder) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.recharge(paymentRechargeOrder);
        PaymentResult paymentResult = rpcRspService.get(responseBody);

        return paymentResult;
    }

    public PaymentResult refund(PaymentRefundOrder refundOrder) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.refund(refundOrder);
        PaymentResult paymentResult = rpcRspService.get(responseBody);

        return paymentResult;
    }

    public PaymentResult withdraw(PaymentWithdrawOrder withdrawOrder) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.withdraw(withdrawOrder);
        PaymentResult paymentResult = rpcRspService.get(responseBody);

        return paymentResult;
    }

    public PaymentResult transfer(PaymentTransferOrder transferOrder) {
        ResponseBody<PaymentResult> responseBody = paymentRpc.transfer(transferOrder);
        PaymentResult paymentResult = rpcRspService.get(responseBody);

        return paymentResult;
    }
}
