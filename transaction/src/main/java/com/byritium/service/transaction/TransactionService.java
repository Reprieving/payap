package com.byritium.service.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentType;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.VirtualCurrency;
import com.byritium.dto.transaction.TradeParam;
import com.byritium.dto.transaction.TransactionOrderMap;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.PaymentExecutor;
import com.byritium.service.TransactionPaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TransactionService {

    @Autowired
    private TransactionOrderService transactionOrderService;

    @Autowired
    private TransactionPaymentOrderService transactionPaymentOrderService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PaymentExecutor paymentExecutor;


    public void trade(TradeParam param) throws ExecutionException, InterruptedException {
        TransactionOrderMap map = new TransactionOrderMap();

        TransactionTradeOrder transactionTradeOrder = new TransactionTradeOrder(param);
        map.setTransactionTradeOrder(transactionTradeOrder);
        {
            PaymentSetting paymentSetting = new PaymentSetting();
            TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder(transactionTradeOrder);
            if (paymentSetting.getChannel() == PaymentChannel.BALANCE_PAY) {
                transactionPaymentOrder.setPaymentType(PaymentType.BALANCE_PAY);
            } else {
                transactionPaymentOrder.setPaymentType(PaymentType.PAYMENT_AGENT);
            }
            map.setPrimaryPaymentOrder(transactionPaymentOrder);
        }

        {
            Long id = param.getDiscountId();
            BigDecimal orderAmount = BigDecimal.ZERO;
            TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder(transactionTradeOrder, PaymentType.DISCOUNT_PAY, id, orderAmount);
            map.setDiscountPaymentOrder(transactionPaymentOrder);
        }

        {
            Long id = param.getCouponId();
            BigDecimal orderAmount = BigDecimal.ZERO;
            TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder(transactionTradeOrder, PaymentType.COUPON_PAY, id, orderAmount);
            map.setCouponPaymentOrder(transactionPaymentOrder);
        }

        {
            VirtualCurrency virtualCurrency = param.getVirtualCurrency();
            BigDecimal orderAmount = virtualCurrency.getAmount();
            TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder(transactionTradeOrder, PaymentType.VIRTUAL_CURRENCY_PAY, virtualCurrency.getId(), orderAmount);
            map.setVirtualCurrencyPaymentOrder(transactionPaymentOrder);
        }


        Boolean flag = transactionTemplate.execute(transactionStatus -> {
            transactionOrderService.save(map.getTransactionTradeOrder());
            List<TransactionPaymentOrder> list = map.getPaymentOrderList();
            transactionPaymentOrderService.saveBatch(list, list.size());
            return transactionStatus.isCompleted();
        });

        if (null == flag || !flag) {
            throw new BusinessException("payment error");
        }


        PaymentResult paymentResult = paymentExecutor.pay(map.getPrimaryPaymentOrder());


    }


}

