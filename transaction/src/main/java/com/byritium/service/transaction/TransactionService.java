package com.byritium.service.transaction;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentType;
import com.byritium.constance.account.AssetsType;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.VirtualCurrency;
import com.byritium.dto.recharge.RechargeProduct;
import com.byritium.dto.transaction.*;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.entity.transaction.TransactionRechargeOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.CashierRpc;
import com.byritium.service.PaymentExecutor;
import com.byritium.service.TransactionPaymentOrderService;
import com.byritium.service.common.ValidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionTradeOrderService transactionTradeOrderService;

    @Autowired
    private TransactionRechargeOrderService transactionRechargeOrderService;

    @Autowired
    private TransactionPaymentOrderService transactionPaymentOrderService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PaymentExecutor paymentExecutor;

    @Autowired
    private CashierRpc cashierRpc;

    @Autowired
    private ValidateService validateService;


    public TransactionResult trade(TradeParam param) {
        TransactionOrderMap<TransactionTradeOrder> map = new TransactionOrderMap<>();

        TransactionTradeOrder transactionTradeOrder = new TransactionTradeOrder(param);
        map.setTransactionOrder(transactionTradeOrder);
        {
            PaymentSetting paymentSetting = cashierRpc.getPaymentSetting(param.getPaymentSettingId());
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
            transactionTradeOrderService.save(map.getTransactionOrder());
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

            if (validateService.anyPaymentFail(p1, p2, p3)) {
                //TODO REFUND ALL OF ORDER
            }

            if (validateService.allPaymentSuccess(p1, p2, p3)) {
                //TODO PAY ALL OF ORDER
            }

            return result;
        } catch (InterruptedException | ExecutionException e) {
            log.error("payment error", e);
            throw new BusinessException("payment error");
        }
    }


    public TransactionResult recharge(RechargeParam rechargeParam) {
        TransactionOrderMap<TransactionRechargeOrder> map = new TransactionOrderMap<>();

        PaymentSetting paymentSetting = cashierRpc.getPaymentSetting(rechargeParam.getPaymentSettingId());
        Long rechargeId = rechargeParam.getRechargeId();
        RechargeProduct rechargeProduct = cashierRpc.getRecharges(rechargeId);

        AssetsType assetsType = rechargeProduct.getAssetsType();
        BigDecimal orderAmount = rechargeProduct.getValue();
        BigDecimal rechargeAmount = rechargeProduct.getNum();

        {
            TransactionRechargeOrder rechargeOrder = new TransactionRechargeOrder();
            rechargeOrder.setUid(rechargeParam.getUid());
            rechargeOrder.setBizOrderId(rechargeParam.getBizOrderId());
            rechargeOrder.setRechargeId(rechargeId);
            rechargeOrder.setAssetsType(assetsType);
            rechargeOrder.setPaymentSettingId(rechargeParam.getPaymentSettingId());
            rechargeOrder.setOrderAmount(orderAmount);
            rechargeOrder.setRechargeAmount(rechargeAmount);
            rechargeOrder.setSubject("");
            map.setTransactionOrder(rechargeOrder);
        }

        {
            TransactionPaymentOrder transactionPaymentOrder = new TransactionPaymentOrder();
            transactionPaymentOrder.setUid(rechargeParam.getUid());
            transactionPaymentOrder.setBizOrderId(rechargeParam.getBizOrderId());
            transactionPaymentOrder.setPayerId(rechargeParam.getUid());
            transactionPaymentOrder.setPayeeId(1L);
            transactionPaymentOrder.setSubject("");
            transactionPaymentOrder.setOrderAmount(orderAmount);
            transactionPaymentOrder.setPaymentType(PaymentType.PAYMENT_AGENT);
            transactionPaymentOrder.setPaymentChannel(paymentSetting.getChannel());
            transactionPaymentOrder.setPaymentSettingId(rechargeParam.getPaymentSettingId());
            map.setPrimaryPaymentOrder(transactionPaymentOrder);
        }

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            transactionRechargeOrderService.save(map.getTransactionOrder());
            List<TransactionPaymentOrder> list = map.getPaymentOrderList();
            transactionPaymentOrderService.saveBatch(list, list.size());
        });


        PaymentResult paymentResult = paymentExecutor.preparePay(map.getPrimaryPaymentOrder());

        TransactionResult result = new TransactionResult();

        if (validateService.anyPaymentFail(paymentResult)) {
            throw new BusinessException("payment error");
        }
        return result;
    }

    public TransactionResult withdraw(WithdrawParam param) {
        return null;
    }


    public TransactionResult transfer(TransferParam param) {
        return null;
    }
}

