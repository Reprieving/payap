package com.byritium.rpc;

import com.byritium.dto.PaymentRequest;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "payment")
@Component
public interface PaymentRpc {
    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody TransactionPaymentOrder transactionPaymentOrder);

}
