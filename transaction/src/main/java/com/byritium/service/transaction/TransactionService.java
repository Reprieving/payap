package com.byritium.service.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentType;
import com.byritium.dto.VirtualCurrency;
import com.byritium.dto.transaction.TradeParam;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.entity.transaction.PaymentOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.service.TransactionPaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionOrderService transactionOrderService;

    @Autowired
    private TransactionPaymentOrderService transactionPaymentOrderService;

    public void trade(TradeParam param) {
        TransactionTradeOrder transactionTradeOrder = new TransactionTradeOrder();
        transactionTradeOrder.setUid(param.getUid());
        transactionTradeOrder.setClientId(param.getClientId());
        transactionTradeOrder.setBizOrderId(param.getBizOrderId());
        transactionTradeOrder.setUid(param.getUid());
        transactionTradeOrder.setPayeeId(param.getPayeeId());
        transactionTradeOrder.setPayeeId(param.getPayeeId());
        transactionTradeOrder.setSubject(param.getSubject());
        transactionTradeOrder.setOrderAmount(param.getOrderAmount());
        transactionTradeOrder.setPaymentSettingId(param.getPaymentSettingId());
        transactionOrderService.save(transactionTradeOrder);

        {
            PaymentSetting paymentSetting = new PaymentSetting();
            PaymentOrder paymentOrder = new PaymentOrder(transactionTradeOrder);
            if (paymentSetting.getChannel() == PaymentChannel.BALANCE_PAY) {
                paymentOrder.setPaymentType(PaymentType.ACCOUNT_PAY);
            } else {
                paymentOrder.setPaymentType(PaymentType.PAYMENT_AGENT);
            }
        }


        {
            List<Long> discountIds = param.getDiscountIds();
            for (Long id : discountIds) {
                BigDecimal orderAmount = BigDecimal.ZERO;
                PaymentOrder paymentOrder = new PaymentOrder(transactionTradeOrder, PaymentType.DISCOUNT_PAY, id, orderAmount);
            }

        }

        {
            List<Long> couponIds = param.getCouponIds();
            for (Long id : couponIds) {
                BigDecimal orderAmount = BigDecimal.ZERO;
                PaymentOrder paymentOrder = new PaymentOrder(transactionTradeOrder, PaymentType.COUPON_PAY, id, orderAmount);
            }
        }

        {
            List<VirtualCurrency> virtualCurrencies = param.getVirtualCurrencies();
            for (VirtualCurrency virtualCurrency : virtualCurrencies) {
                BigDecimal orderAmount = virtualCurrency.getAmount();
                PaymentOrder paymentOrder = new PaymentOrder(transactionTradeOrder, PaymentType.PAYMENT_AGENT, virtualCurrency.getId(), orderAmount);
            }
        }


    }


}

