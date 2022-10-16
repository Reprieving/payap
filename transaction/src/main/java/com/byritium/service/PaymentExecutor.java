package com.byritium.service;

import com.byritium.constance.PaymentType;
import com.byritium.entity.payment.PaymentOrder;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.MarketingRpc;
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
    private MarketingRpc marketingRpc;


    public void pay(TransactionPaymentOrder transactionPaymentOrder) {
        PaymentType paymentType = transactionPaymentOrder.getPaymentType();
        switch (paymentType) {
            case ACCOUNT_PAY:
                accountRpc.pay(transactionPaymentOrder);
                break;

            case PAYMENT_AGENT:
                paymentRpc.pay(transactionPaymentOrder);
                break;

            case COUPON_PAY:
                marketingRpc.lockCoupon(transactionPaymentOrder);
                break;

            case DISCOUNT_PAY:
                marketingRpc.discountRecord(transactionPaymentOrder);
                break;
        }

    }

    public void payCallback(TransactionPaymentOrder transactionPaymentOrder) {

    }

}
