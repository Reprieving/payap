package com.byritium.rpc;

import com.byritium.dto.AccountBalance;
import com.byritium.dto.AccountQuery;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.transaction.PayOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "account")
@Component
public interface AccountRpc {
    @RequestMapping("pay")
    ResponseBody<Void> pay(PayOrder payOrder);

    @RequestMapping("lock")
    ResponseBody<Void> lock(PayOrder payOrder);

    @RequestMapping("query")
    ResponseBody<AccountBalance> query(AccountQuery accountQuery);
}
