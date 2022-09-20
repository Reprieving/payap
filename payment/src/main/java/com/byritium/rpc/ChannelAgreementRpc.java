package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "channel")
@Component
public interface ChannelAgreementRpc {//渠道网关签约

    @RequestMapping("sign")
    ResponseBody<PaymentResult> pay();

    @RequestMapping("rescind")
    ResponseBody<PaymentResult> rescind();

    @RequestMapping("query")
    ResponseBody<PaymentResult> query();
}
