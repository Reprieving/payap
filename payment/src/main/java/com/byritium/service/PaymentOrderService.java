package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.constance.PaymentState;
import com.byritium.dao.PaymentOrderRepository;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import com.byritium.entity.PaymentOrder;
import com.byritium.service.channel.PayWrapperService;
import com.byritium.service.channel.QueryWrapperService;
import com.byritium.service.channel.RefundWrapperService;
import com.byritium.service.channel.WithdrawWrapperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class PaymentOrderService {

    @Resource
    private PayWrapperService payWrapperService;

    @Resource
    private RefundWrapperService refundWrapperService;

    @Resource
    private WithdrawWrapperService withdrawWrapperService;

    @Resource
    private QueryWrapperService queryWrapperService;

    @Resource
    private PaymentOrderRepository paymentOrderRepository;

    public void pay(PaymentOrder paymentOrder) {
        PaymentProduct paymentProduct = paymentOrder.getPaymentProduct();
        PaymentChannel paymentChannel = paymentOrder.getPaymentChannel();

        PaymentState paymentState = paymentOrder.getPaymentState();


        PaymentExtra paymentExtra = new PaymentExtra();
        paymentExtra.setPaymentChannel(paymentChannel);
        paymentExtra.setPaymentProduct(paymentProduct);

        PaymentOrder save = paymentOrderRepository.save(paymentOrder);
        String paymentOrderId = save.getId();


        PayParam payParam;
        switch (paymentState) {
            case PAYMENT_PENDING:
                payParam = payWrapperService.pay(paymentOrderId, paymentOrder.getSubject(), paymentOrder.getPayAmount(), paymentExtra);

                break;

            case PAYMENT_SUCCESS:
                //查询支付状态

                //调用清算服务

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

        refundWrapperService.refund(businessOrderId, refundOrderId, orderAmount, refundAmount, paymentExtra);

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


        withdrawWrapperService.withdraw(businessOrderId, sdkId, withdrawAmount, paymentExtra);

    }

    public void query(PaymentOrder paymentOrder) {
        PaymentProduct paymentProduct = paymentOrder.getPaymentProduct();
        PaymentChannel paymentChannel = paymentOrder.getPaymentChannel();

        PaymentState paymentState = paymentOrder.getPaymentState();


        PaymentExtra paymentExtra = new PaymentExtra();
        paymentExtra.setPaymentChannel(paymentChannel);
        paymentExtra.setPaymentProduct(paymentProduct);

        String businessOrderId = "";

        queryWrapperService.query(businessOrderId, paymentExtra);

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
