package com.byritium.rpc;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "gateway")
public interface PaymentPayRpc {


}
