package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.TransactionPayOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "gateway")
public interface PaymentPayRpc {
    @RequestMapping("payment/pay")
    ResponseBody<PaymentResult> pay(TransactionPayOrder transactionPayOrder);

}
