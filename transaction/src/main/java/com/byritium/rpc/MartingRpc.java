package com.byritium.rpc;

import com.byritium.dto.CouponInfo;
import com.byritium.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "coupon")
public interface MartingRpc {
    @RequestMapping("get")
    ResponseBody<CouponInfo> get(String couponId);
}
