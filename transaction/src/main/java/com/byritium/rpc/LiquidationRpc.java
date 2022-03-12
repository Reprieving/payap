package com.byritium.rpc;

import com.byritium.dto.LiquidationParam;
import com.byritium.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "liquidation")
public interface LiquidationRpc {
    @RequestMapping("payment")
    ResponseBody<Void> payment(LiquidationParam param);

    @RequestMapping("calculate")
    ResponseBody<Void> calculate(LiquidationParam param);

    @RequestMapping("refund")
    ResponseBody<Void> refund(LiquidationParam param);

    @RequestMapping("settle")
    ResponseBody<Void> settle(LiquidationParam param);
}
