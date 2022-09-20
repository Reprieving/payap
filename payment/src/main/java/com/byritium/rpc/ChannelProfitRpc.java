package com.byritium.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = "channel")
@Component
public interface ChannelProfitRpc {//渠道网关分账

}
