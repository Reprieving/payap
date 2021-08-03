package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionProduct;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import com.byritium.entity.PaymentOrder;
import com.byritium.service.channel.PayModelService;
import com.byritium.service.channel.QueryModelService;
import com.byritium.service.channel.RefundModelService;
import com.byritium.service.channel.WithdrawModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class PaymentOrderService {

    @Resource
    private PayModelService payModelService;

    @Resource
    private RefundModelService refundModelService;

    @Resource
    private WithdrawModelService withdrawModelService;

    @Resource
    private QueryModelService queryModelService;

    public void pay(PaymentOrder paymentOrder) {
        PaymentProduct paymentProduct = paymentOrder.getPaymentProduct();
        PaymentChannel paymentChannel = paymentOrder.getPaymentChannel();

        PaymentState paymentState = paymentOrder.getPaymentState();


        PaymentExtra paymentExtra = new PaymentExtra();
        paymentExtra.setPaymentChannel(paymentChannel);
        paymentExtra.setPaymentProduct(paymentProduct);

        String businessOrderId = paymentOrder.getBusinessOrderId();


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

    public void refund(PaymentOrder paymentOrder) {
        PaymentProduct paymentProduct = paymentOrder.getPaymentProduct();
        PaymentChannel paymentChannel = paymentOrder.getPaymentChannel();


        PaymentExtra paymentExtra = new PaymentExtra();
        paymentExtra.setPaymentChannel(paymentChannel);
        paymentExtra.setPaymentProduct(paymentProduct);

        String businessOrderId = "";
        String refundOrderId = "";
        BigDecimal orderAmount = BigDecimal.valueOf(0);
        BigDecimal refundAmount = BigDecimal.valueOf(0);

        refundModelService.refund(businessOrderId, refundOrderId, orderAmount, refundAmount, paymentExtra);

    }


    public void withdraw(PaymentOrder paymentOrder) {
        PaymentProduct paymentProduct = paymentOrder.getPaymentProduct();
        PaymentChannel paymentChannel = paymentOrder.getPaymentChannel();


        PaymentExtra paymentExtra = new PaymentExtra();
        paymentExtra.setPaymentChannel(paymentChannel);
        paymentExtra.setPaymentProduct(paymentProduct);

        String businessOrderId = "";
        String sdkId = "";
        BigDecimal withdrawAmount = BigDecimal.valueOf(0);


        withdrawModelService.withdraw(businessOrderId, sdkId, withdrawAmount, paymentExtra);

    }

    public void query(PaymentOrder paymentOrder) {
        PaymentProduct paymentProduct = paymentOrder.getPaymentProduct();
        PaymentChannel paymentChannel = paymentOrder.getPaymentChannel();

        PaymentState paymentState = paymentOrder.getPaymentState();


        PaymentExtra paymentExtra = new PaymentExtra();
        paymentExtra.setPaymentChannel(paymentChannel);
        paymentExtra.setPaymentProduct(paymentProduct);

        String businessOrderId = "";

        queryModelService.query(businessOrderId, paymentExtra);

    }

    public void settle(PaymentOrder paymentOrder) {
        PaymentProduct paymentProduct = paymentOrder.getPaymentProduct();
        PaymentChannel paymentChannel = paymentOrder.getPaymentChannel();

        PaymentState paymentState = paymentOrder.getPaymentState();


        PaymentExtra paymentExtra = new PaymentExtra();
        paymentExtra.setPaymentChannel(paymentChannel);
        paymentExtra.setPaymentProduct(paymentProduct);

    }
}
