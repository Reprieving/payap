package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.transaction.PayOrder;
import com.byritium.entity.transaction.WithdrawOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "payment")
@Component
public interface PaymentRpc {
    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody PayOrder payOrder);

    @RequestMapping("withdraw")
    ResponseBody<PaymentResult> withdraw(@RequestBody WithdrawOrder withdrawOrder);

}
