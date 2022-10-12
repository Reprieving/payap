package com.byritium.rpc;

import com.byritium.dto.AccountBalance;
import com.byritium.dto.AccountQuery;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@FeignClient(value = "account")
public interface AccountRpc {
    @RequestMapping("pay")
    ResponseBody<Void> pay(AccountJournal accountJournal);

    @RequestMapping("query")
    ResponseBody<AccountBalance> query(AccountQuery accountQuery);
}
