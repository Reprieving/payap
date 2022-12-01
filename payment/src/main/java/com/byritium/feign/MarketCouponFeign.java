package com.byritium.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = "market/pay")
@Component
public interface MarketCouponFeign {
}
