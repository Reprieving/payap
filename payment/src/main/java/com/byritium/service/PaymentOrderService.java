package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionProduct;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import com.byritium.entity.PaymentOrder;
import com.byritium.service.channel.PayModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentOrderService {

    @Resource
    private PayModelService payModelService;

    public void pay(PaymentOrder paymentOrder) {
        PaymentProduct paymentProduct = paymentOrder.getPaymentProduct();
        PaymentChannel paymentChannel = paymentOrder.getPaymentChannel();

        PaymentState paymentState = paymentOrder.getPaymentState();


        PaymentExtra paymentExtra = new PaymentExtra();
        paymentExtra.setPaymentChannel(paymentChannel);
        paymentExtra.setPaymentProduct(paymentProduct);


        PayParam payParam;
        switch (paymentState) {
            case PAYMENT_PENDING:
                payParam = payModelService.pay(paymentOrder.getBusinessOrderId(), paymentOrder.getSubject(), paymentOrder.getPayAmount(), paymentExtra);

                break;

            case PAYMENT_SUCCESS:
                //查询支付状态

                //调用清算服务，确定扣款人id和账户，收款人id和账户

                //通知交易服务

                break;

            case PAYMENT_FAIL:
                //通知交易服务

                break;
        }
    }

}
