package com.byritium.service;

import com.byritium.constance.PaymentType;
import com.byritium.dto.PaymentResult;
import com.byritium.entity.transaction.PayOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.MarketingCouponRpc;
import com.byritium.rpc.MarketingDiscountRpc;
import com.byritium.rpc.PaymentRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentExecutor {

    @Autowired
    private AccountRpc accountRpc;

    @Autowired
    private PaymentRpc paymentRpc;

    @Autowired
    private MarketingCouponRpc marketingCouponRpc;

    @Autowired
    private MarketingDiscountRpc marketingDiscountRpc;

    public PaymentResult preparePay(PayOrder payOrder) {
        PaymentType paymentType = payOrder.getPaymentType();
        switch (paymentType) {
            case BALANCE_PAY:
            case VIRTUAL_CURRENCY_PAY:
                accountRpc.pay(payOrder);
                break;

            case PAYMENT_AGENT:
                paymentRpc.pay(payOrder);
                break;

            case COUPON_PAY:
                marketingCouponRpc.clip(payOrder);
                break;

            case DISCOUNT_PAY:
                marketingDiscountRpc.record(payOrder);
                break;
        }

        return null;
    }

}
