package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.service.callback.entity.PayOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "channel")
@Component
public interface ChannelPaymentRpc {

    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody PayOrder payOrder);

    @RequestMapping("withdraw")
    ResponseBody<PaymentResult> withdraw();

    @RequestMapping("transfer")
    ResponseBody<PaymentResult> transfer();

    @RequestMapping("refund")
    ResponseBody<PaymentResult> refund();

}
