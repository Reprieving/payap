package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.entity.TransactionRefundOrder;
import com.byritium.entity.TransactionSettleOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "payment")
public interface PaymentPayRpc {
    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody TransactionPaymentOrder transactionPaymentOrder);

    @RequestMapping("settle")
    ResponseBody<PaymentResult> settle(@RequestBody TransactionSettleOrder transactionSettleOrder);

    @RequestMapping("refund")
    ResponseBody<PaymentResult> refund(@RequestBody TransactionRefundOrder transactionRefundOrder);


}
