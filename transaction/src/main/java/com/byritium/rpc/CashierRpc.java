package com.byritium.rpc;

import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.dto.recharge.RechargeProduct;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "cashier")
@Component
public interface CashierRpc {
    @RequestMapping("paymentSetting/get")
    PaymentSetting getPaymentSetting(Long paymentSettingId);

    @RequestMapping("recharges/get")
    RechargeProduct getRecharges(Long rechargeId);

}
