package com.byritium.service.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentType;
import com.byritium.dto.VirtualCurrency;
import com.byritium.dto.transaction.TradeParam;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.service.TransactionPaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionOrderService transactionOrderService;

    @Autowired
    private TransactionPaymentOrderService transactionPaymentOrderService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void trade(TradeParam param) {
        Boolean flag = transactionTemplate.execute(transactionStatus -> {
            TransactionTradeOrder transactionTradeOrder = new TransactionTradeOrder(param);
            transactionOrderService.save(transactionTradeOrder);
            {
                PaymentSetting paymentSetting = new PaymentSetting();
                TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder(transactionTradeOrder);
                if (paymentSetting.getChannel() == PaymentChannel.BALANCE_PAY) {
                    transactionPaymentOrder.setPaymentType(PaymentType.ACCOUNT_PAY);
                } else {
                    transactionPaymentOrder.setPaymentType(PaymentType.PAYMENT_AGENT);
                }

                transactionPaymentOrderService.save(transactionPaymentOrder);
            }


            {
                List<Long> discountIds = param.getDiscountIds();
                for (Long id : discountIds) {
                    BigDecimal orderAmount = BigDecimal.ZERO;
                    TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder(transactionTradeOrder, PaymentType.DISCOUNT_PAY, id, orderAmount);
                    transactionPaymentOrderService.save(transactionPaymentOrder);
                }

            }

            {
                List<Long> couponIds = param.getCouponIds();
                for (Long id : couponIds) {
                    BigDecimal orderAmount = BigDecimal.ZERO;
                    TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder(transactionTradeOrder, PaymentType.COUPON_PAY, id, orderAmount);
                    transactionPaymentOrderService.save(transactionPaymentOrder);
                }
            }

            {
                List<VirtualCurrency> virtualCurrencies = param.getVirtualCurrencies();
                for (VirtualCurrency virtualCurrency : virtualCurrencies) {
                    BigDecimal orderAmount = virtualCurrency.getAmount();
                    TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder(transactionTradeOrder, PaymentType.ACCOUNT_PAY, virtualCurrency.getId(), orderAmount);
                    transactionPaymentOrderService.save(transactionPaymentOrder);
                }
            }

            return transactionStatus.isCompleted();
        });


    }


}

