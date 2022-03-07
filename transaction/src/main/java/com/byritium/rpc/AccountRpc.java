package com.byritium.rpc;

import com.byritium.dto.AccountJournal;
import com.byritium.dto.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "account")
public interface AccountRpc {
    @RequestMapping("record")
    ResponseBody<Void> record(AccountJournal accountJournal);
}
