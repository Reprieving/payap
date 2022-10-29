package com.byritium.service.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.PaymentType;
import com.byritium.constance.account.AssetsType;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.VirtualCurrency;
import com.byritium.dto.transaction.RechargeParam;
import com.byritium.dto.transaction.TradeParam;
import com.byritium.dto.transaction.TransactionOrderMap;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.PaymentExecutor;
import com.byritium.service.TransactionPaymentOrderService;
import com.byritium.service.common.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

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

    @Autowired
    private ValidateService validateService;


    public TransactionResult trade(TradeParam param) {
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

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            transactionOrderService.save(map.getTransactionTradeOrder());
            List<TransactionPaymentOrder> list = map.getPaymentOrderList();
            transactionPaymentOrderService.saveBatch(list, list.size());
        });

        CompletableFuture<PaymentResult> c1 = CompletableFuture.supplyAsync(() -> paymentExecutor.preparePay(map.getPrimaryPaymentOrder()));
        CompletableFuture<PaymentResult> c2 = CompletableFuture.supplyAsync(() -> paymentExecutor.preparePay(map.getCouponPaymentOrder()));
        CompletableFuture<PaymentResult> c3 = CompletableFuture.supplyAsync(() -> paymentExecutor.preparePay(map.getVirtualCurrencyPaymentOrder()));


        TransactionResult result = new TransactionResult();
        try {
            PaymentResult p1 = c1.get();
            PaymentResult p2 = c2.get();
            PaymentResult p3 = c3.get();

            if (validateService.anyPaymentFail(p1,p2,p3)) {
                //TODO REFUND ALL OF ORDER
            }

            if (validateService.allPaymentSuccess(p1,p2,p3)) {
                //TODO PAY ALL OF ORDER
            }

            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public void recharge(RechargeParam rechargeParam){
        Long rechargeId = rechargeParam.getRechargeId();

        //TODO get recharge info from cashier api

        AssetsType assetsType = AssetsType.RMB;
        BigDecimal orderAmount = BigDecimal.ZERO;



        switch (assetsType){
            case RMB:

                break;
            case VIRTUAL_CURRENCY:

                break;
        }
    }


}

