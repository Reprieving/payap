package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.TransactionPayOrder;
import com.byritium.entity.TransactionRefundOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "payment")
public interface PaymentPayRpc {
    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody TransactionPayOrder transactionPayOrder);

    @RequestMapping("refund")
    ResponseBody<PaymentResult> refund(@RequestBody TransactionRefundOrder transactionRefundOrder);
}
