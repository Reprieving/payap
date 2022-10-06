package com.byritium.rpc;

import com.byritium.dto.alipay.AliPayConfig;
import com.byritium.dto.wechat.WechatPayConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "secret")
public interface SecretFeign {
    WechatPayConfig getWechatPayConfig();

    AliPayConfig getAliPayConfig();
}
