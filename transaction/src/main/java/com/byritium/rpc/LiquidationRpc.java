package com.byritium.rpc;

import com.byritium.dto.AccountJournal;
import com.byritium.dto.LiquidationParam;
import com.byritium.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "liquidation")
public interface LiquidationRpc {
    @RequestMapping("call")
    ResponseBody<Void> call(LiquidationParam param);
}
