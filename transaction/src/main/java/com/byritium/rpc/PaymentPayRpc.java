package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.PaymentOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "payment")
public interface PaymentPayRpc {
    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody PaymentOrder paymentOrder);

    @RequestMapping("settle")
    ResponseBody<PaymentResult> settle(@RequestBody PaymentOrder transactionSettleOrder);

    @RequestMapping("refund")
    ResponseBody<PaymentResult> refund(@RequestBody PaymentOrder transactionRefundOrder);


}
