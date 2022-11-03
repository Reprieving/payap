package com.byritium.service.transaction;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentType;
import com.byritium.constance.account.AssetsType;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.VirtualCurrency;
import com.byritium.dto.recharge.RechargeProduct;
import com.byritium.dto.transaction.*;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.entity.transaction.*;
import com.byritium.exception.BusinessException;
import com.byritium.rpc.CashierRpc;
import com.byritium.service.PaymentExecutor;
import com.byritium.service.PaymentOrderService;
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
    private TradeOrderService tradeOrderService;

    @Autowired
    private RechargeOrderService rechargeOrderService;

    @Autowired
    private PaymentOrderService paymentOrderService;



    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PaymentExecutor paymentExecutor;

    @Autowired
    private CashierRpc cashierRpc;

    @Autowired
    private ValidateService validateService;


    public TransactionResult trade(TradeParam param) {
        TransactionOrderMap<TradeOrder> map = new TransactionOrderMap<>();

        TradeOrder tradeOrder = new TradeOrder(param);
        map.setTransactionOrder(tradeOrder);
        {
            PaymentSetting paymentSetting = cashierRpc.getPaymentSetting(param.getPaymentSettingId());
            PayOrder payOrder = new PayOrder(tradeOrder);
            if (paymentSetting.getChannel() == PaymentChannel.BALANCE_PAY) {
                payOrder.setPaymentType(PaymentType.BALANCE_PAY);
            } else {
                payOrder.setPaymentType(PaymentType.PAYMENT_AGENT);
            }
            map.setPrimaryPaymentOrder(payOrder);
        }

        {
            Long id = param.getDiscountId();
            BigDecimal orderAmount = BigDecimal.ZERO;
            PayOrder payOrder = new PayOrder(tradeOrder, PaymentType.DISCOUNT_PAY, id, orderAmount);
            map.setDiscountPaymentOrder(payOrder);
        }

        {
            Long id = param.getCouponId();
            BigDecimal orderAmount = BigDecimal.ZERO;
            PayOrder payOrder = new PayOrder(tradeOrder, PaymentType.COUPON_PAY, id, orderAmount);
            map.setCouponPaymentOrder(payOrder);
        }

        {
            VirtualCurrency virtualCurrency = param.getVirtualCurrency();
            BigDecimal orderAmount = virtualCurrency.getAmount();
            PayOrder payOrder = new PayOrder(tradeOrder, PaymentType.VIRTUAL_CURRENCY_PAY, virtualCurrency.getId(), orderAmount);
            map.setVirtualCurrencyPaymentOrder(payOrder);
        }

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            tradeOrderService.save(map.getTransactionOrder());
            List<PayOrder> list = map.getPaymentOrderList();
            paymentOrderService.saveBatch(list, list.size());
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
        TransactionOrderMap<RechargeOrder> map = new TransactionOrderMap<>();

        PaymentSetting paymentSetting = cashierRpc.getPaymentSetting(rechargeParam.getPaymentSettingId());
        Long rechargeId = rechargeParam.getRechargeId();
        RechargeProduct rechargeProduct = cashierRpc.getRecharges(rechargeId);

        AssetsType assetsType = rechargeProduct.getAssetsType();
        BigDecimal orderAmount = rechargeProduct.getValue();
        BigDecimal rechargeAmount = rechargeProduct.getNum();

        {
            RechargeOrder rechargeOrder = new RechargeOrder();
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
            PayOrder payOrder = new PayOrder();
            payOrder.setUid(rechargeParam.getUid());
            payOrder.setBizOrderId(rechargeParam.getBizOrderId());
            payOrder.setPayerId(rechargeParam.getUid());
            payOrder.setPayeeId(1L);
            payOrder.setSubject("");
            payOrder.setOrderAmount(orderAmount);
            payOrder.setPaymentType(PaymentType.PAYMENT_AGENT);
            payOrder.setPaymentChannel(paymentSetting.getChannel());
            payOrder.setPaymentSettingId(rechargeParam.getPaymentSettingId());
            map.setPrimaryPaymentOrder(payOrder);
        }

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            rechargeOrderService.save(map.getTransactionOrder());
            List<PayOrder> list = map.getPaymentOrderList();
            paymentOrderService.saveBatch(list, list.size());
        });


        PaymentResult paymentResult = paymentExecutor.preparePay(map.getPrimaryPaymentOrder());

        TransactionResult result = new TransactionResult();

        if (validateService.anyPaymentFail(paymentResult)) {
            throw new BusinessException("payment error");
        }
        return result;
    }


    public TransactionResult transfer(TransferParam param) {
        return null;
    }
}

