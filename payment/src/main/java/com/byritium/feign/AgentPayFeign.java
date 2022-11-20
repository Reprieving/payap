package com.byritium.feign;

import com.byritium.dto.AgentPayParaml;
import com.byritium.dto.PaymentResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "agent/pay")
@Component
public interface AgentPayFeign {
    @RequestMapping("order")
    PaymentResult order(@RequestBody AgentPayParaml agentPayParaml);

    @RequestMapping("query")
    PaymentResult query(Long payOrderId);

    @RequestMapping("close")
    PaymentResult close(Long payOrderId);
}
