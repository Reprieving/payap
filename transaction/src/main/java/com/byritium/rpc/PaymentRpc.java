package com.byritium.rpc;

import com.byritium.dto.PaymentRequest;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.transaction.TransactionPayOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "payment")
@Component
public interface PaymentRpc {
    @RequestMapping("call")
    ResponseBody<PaymentResult> call(@RequestBody PaymentRequest paymentRequest);


    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody TransactionPayOrder transactionPayOrder);
}
