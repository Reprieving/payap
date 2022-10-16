package com.byritium.rpc;

import com.byritium.dto.CouponInfo;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "marketing")
@Component
public interface MarketingRpc {
    @RequestMapping("get")
    ResponseBody<CouponInfo> get(String couponId);

    void lockCoupon(TransactionPaymentOrder transactionPaymentOrder);

    void discountRecord(TransactionPaymentOrder transactionPaymentOrder);
}
