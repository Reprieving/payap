package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.PayOrder;
import com.byritium.entity.RefundOrder;
import com.byritium.entity.SettleOrder;
import com.byritium.entity.TransferOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "payment")
public interface PaymentRpc {
    @RequestMapping("pay")
    ResponseBody<PaymentResult> pay(@RequestBody PayOrder payOrder);

    @RequestMapping("settle")
    ResponseBody<PaymentResult> settle(@RequestBody SettleOrder settleOrder);

    @RequestMapping("refund")
    ResponseBody<PaymentResult> refund(@RequestBody RefundOrder refundOrder);

    @RequestMapping("transfer")
    ResponseBody<PaymentResult> transfer(@RequestBody TransferOrder transferOrder);

}
