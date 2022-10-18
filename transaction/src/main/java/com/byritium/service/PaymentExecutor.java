package com.byritium.service;

import com.byritium.constance.PaymentType;
import com.byritium.dto.PaymentResult;
import com.byritium.entity.transaction.TransactionPaymentOrder;
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

    public PaymentResult pay(TransactionPaymentOrder transactionPaymentOrder) {
        PaymentType paymentType = transactionPaymentOrder.getPaymentType();
        switch (paymentType) {
            case BALANCE_PAY:
                accountRpc.pay(transactionPaymentOrder);
                break;

            case PAYMENT_AGENT:
                paymentRpc.pay(transactionPaymentOrder);
                break;

            case COUPON_PAY:
                marketingCouponRpc.lock(transactionPaymentOrder);
                break;

            case DISCOUNT_PAY:
                marketingDiscountRpc.record(transactionPaymentOrder);
                break;

            case VIRTUAL_CURRENCY_PAY:
                accountRpc.lock(transactionPaymentOrder);
                break;
        }

        return null;
    }

    public void payCallback(TransactionPaymentOrder transactionPaymentOrder) {
        PaymentType paymentType = transactionPaymentOrder.getPaymentType();
        if (paymentType == PaymentType.COUPON_PAY) {
            marketingCouponRpc.clip(transactionPaymentOrder);
        }
    }

}