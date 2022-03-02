package com.byritium.rpc;

import com.byritium.dto.AccountJournal;
import com.byritium.dto.CouponInfo;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.TransactionPayOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "account")
public interface AccountRpc {
    @RequestMapping("record")
    ResponseBody<Void> record(AccountJournal accountJournal);
}