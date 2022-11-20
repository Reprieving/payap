package com.byritium.feign;

import com.byritium.dto.AgentPayParaml;
import com.byritium.dto.PaymentResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "account/recorded")
@Component
public interface AccountRecordedFeign {
    @RequestMapping("execute")
    PaymentResult execute(@RequestBody AgentPayParaml agentPayParaml);
    
    @RequestMapping("query")
    PaymentResult query(@RequestBody AgentPayParaml agentPayParaml);
}
