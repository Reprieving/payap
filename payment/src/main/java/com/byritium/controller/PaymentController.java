package com.byritium.controller;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProductType;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionProductType;
import com.byritium.entity.PaymentOrder;
import com.byritium.entity.PaymentProduct;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @RequestMapping("pay")
    public void pay(@RequestBody PaymentOrder paymentOrder) {
        TransactionProductType transactionProductType = paymentOrder.getTransactionProductType();

        //DB QUERY
        PaymentProductType paymentProductType = new PaymentProduct().getPaymentProductType();
        PaymentChannel paymentChannel = paymentOrder.getPaymentChannel();



        PaymentState paymentState = paymentOrder.getPaymentState();
        if (paymentState == PaymentState.PAYMENT_SUCCESS) {
            //记账
        }


    }

    @RequestMapping("refund")
    public void refund() {

    }

    @RequestMapping("settle")
    public void settle() {

    }

    @RequestMapping("withdraw")
    public void query() {

    }

}
