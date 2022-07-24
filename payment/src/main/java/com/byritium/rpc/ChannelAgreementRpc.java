package com.byritium.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = "channel")
@Component
public interface ChannelAgreementRpc {//渠道网关签约

}
