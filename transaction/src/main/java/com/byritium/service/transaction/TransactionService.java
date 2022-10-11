package com.byritium.service.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentChannelType;
import com.byritium.dto.VirtualCurrency;
import com.byritium.dto.transaction.TradeParam;
import com.byritium.entity.transaction.PaymentOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.service.TransactionPaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
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
        transactionTradeOrder.setUid(param.getClientId());
        transactionTradeOrder.setPayeeId(param.getClientId());
        transactionTradeOrder.setPayeeId(param.getClientId());
        transactionTradeOrder.setSubject(param.getSubject());
        transactionTradeOrder.setOrderAmount(param.getOrderAmount());
        transactionTradeOrder.setPaymentPatternId(param.getPaymentPatternId());
        transactionOrderService.save(transactionTradeOrder);


        if (param.getPaymentPatternId() != null) {
            PaymentOrder paymentOrder = new PaymentOrder();
            paymentOrder.setUid(transactionTradeOrder.getUid());
            paymentOrder.setBizOrderId(transactionTradeOrder.getBizOrderId());
            paymentOrder.setTxOrderId(transactionTradeOrder.getId());
            paymentOrder.setPayerId(transactionTradeOrder.getPayerId());
            paymentOrder.setPayeeId(transactionTradeOrder.getPayeeId());
            paymentOrder.setSubject(transactionTradeOrder.getSubject());
            paymentOrder.setOrderAmount(transactionTradeOrder.getOrderAmount());
            paymentOrder.setPaymentPatternId(transactionTradeOrder.getPaymentPatternId());
        }

        {
            List<Long> discountIds = param.getDiscountIds();
            for (Long id : discountIds) {
                BigDecimal orderAmount = BigDecimal.ZERO;
                PaymentOrder paymentOrder = new PaymentOrder(transactionTradeOrder, PaymentChannelType.MARKETING, id, orderAmount);
            }

        }

        {
            List<Long> couponIds = param.getCouponIds();
            for (Long id : couponIds) {
                BigDecimal orderAmount = BigDecimal.ZERO;
                PaymentOrder paymentOrder = new PaymentOrder(transactionTradeOrder, PaymentChannelType.MARKETING, id, orderAmount);
            }
        }

        {
            List<VirtualCurrency> virtualCurrencies = param.getVirtualCurrencies();
            for (VirtualCurrency virtualCurrency : virtualCurrencies) {
                BigDecimal orderAmount = virtualCurrency.getAmount();
                PaymentOrder paymentOrder = new PaymentOrder(transactionTradeOrder, PaymentChannelType.ACCOUNT_PAY, virtualCurrency.getId(), orderAmount);
            }
        }


    }


}
