package com.byritium.rpc;

import com.byritium.dto.AliPayConfig;
import com.byritium.dto.WechatPayConfig;
import org.springframework.cloud.client.loadbalancer.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

@FeignClient(value = "secret")
public interface SecretFeign {
    WechatPayConfig getWechatPayConfig();

    AliPayConfig getAliPayConfig();
}
