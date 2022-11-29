package com.byritium.feign;

import com.byritium.dto.AccountRecordedParam;
import com.byritium.dto.PaymentResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "account/pay")
@Component
public interface AccountPayFeign {
    @RequestMapping("execute")
    PaymentResult execute(@RequestBody AccountRecordedParam param);
    
    @RequestMapping("query")
    PaymentResult query(Long paymentOrderId);
}
