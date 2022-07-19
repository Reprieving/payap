package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.PayOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "channel")
@Component
public interface ChannelPaymentRpc {

    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody PayOrder payOrder);
}
