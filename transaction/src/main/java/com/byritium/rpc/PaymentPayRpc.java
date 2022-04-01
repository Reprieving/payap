package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.PayOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "payment")
public interface PaymentPayRpc {
    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody PayOrder payOrder);

    @RequestMapping("settle")
    ResponseBody<PaymentResult> settle(@RequestBody PayOrder transactionSettleOrder);

    @RequestMapping("refund")
    ResponseBody<PaymentResult> refund(@RequestBody PayOrder transactionRefundOrder);


}
