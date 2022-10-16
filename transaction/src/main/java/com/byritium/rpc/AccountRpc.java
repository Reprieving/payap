package com.byritium.rpc;

import com.byritium.dto.AccountBalance;
import com.byritium.dto.AccountQuery;
import com.byritium.dto.AccountJournal;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@FeignClient(value = "account")
@Component
public interface AccountRpc {
    @RequestMapping("pay")
    ResponseBody<Void> pay(TransactionPaymentOrder transactionPaymentOrder);

    @RequestMapping("lock")
    ResponseBody<Void> lock(TransactionPaymentOrder transactionPaymentOrder);

    @RequestMapping("query")
    ResponseBody<AccountBalance> query(AccountQuery accountQuery);
}
