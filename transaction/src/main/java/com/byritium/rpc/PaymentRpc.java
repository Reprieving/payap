package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.payment.PaymentRechargeOrder;
import com.byritium.entity.payment.PaymentRefundOrder;
import com.byritium.entity.payment.PaymentTransferOrder;
import com.byritium.entity.payment.PaymentWithdrawOrder;
import com.byritium.entity.transaction.PaymentTransferOrderOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "payment")
public interface PaymentRpc {
    @RequestMapping("recharge")
    ResponseBody<PaymentResult> recharge(@RequestBody PaymentRechargeOrder paymentRechargeOrder);

    @RequestMapping("withdraw")
    ResponseBody<PaymentResult> withdraw(@RequestBody PaymentWithdrawOrder paymentWithdrawOrder);

    @RequestMapping("refund")
    ResponseBody<PaymentResult> refund(@RequestBody PaymentRefundOrder refundOrder);

    @RequestMapping("transfer")
    ResponseBody<PaymentResult> transfer(@RequestBody PaymentTransferOrder transferOrder);

}
