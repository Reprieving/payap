package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "channel")
@Component
public interface ChannelPayRpc {

    @RequestMapping("call")
    ResponseBody<PaymentResult> call();

    @RequestMapping("query")
    ResponseBody<PaymentResult> withdraw();

}
