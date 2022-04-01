package com.byritium.service.transaction.impl;

import com.byritium.constance.*;
import com.byritium.dto.*;
import com.byritium.entity.RefundOrder;
import com.byritium.entity.TransactionOrder;
import com.byritium.exception.BusinessException;
import com.byritium.service.payment.impl.RefundPaymentService;
import com.byritium.service.transaction.ITransactionService;
import com.byritium.service.transaction.TransactionOrderService;
import com.byritium.service.payment.PaymentOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

@Service
public class RefundTransactionService implements ITransactionService {
    public RefundTransactionService(TransactionTemplate transactionTemplate, TransactionOrderService transactionOrderService, PaymentOrderService paymentOrderService, RefundPaymentService refundPaymentService) {
        this.transactionTemplate = transactionTemplate;
        this.transactionOrderService = transactionOrderService;
        this.paymentOrderService = paymentOrderService;
        this.refundPaymentService = refundPaymentService;
    }

    private final TransactionTemplate transactionTemplate;
    private final TransactionOrderService transactionOrderService;
    private final PaymentOrderService paymentOrderService;
    private final RefundPaymentService refundPaymentService;

    @Override
    public TransactionType type() {
        return TransactionType.REFUND;
    }

    @Override
    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = new TransactionResult();

        TransactionOrder transactionOrder = transactionOrderService.findByBizOrderId(param.getBusinessOrderId());
        if (transactionOrder == null) {
            throw new BusinessException("未找到订单");
        }

        if (param.getOrderAmount().compareTo(transactionOrder.getPayAmount()) > 0 || param.getOrderAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("退款金额异常");
        }

        RefundOrder refundOrder = new RefundOrder();

        TransactionState transactionState = transactionOrder.getTransactionState();
        String transactionOrderId = transactionOrder.getId();
        PaymentChannel paymentChannel = transactionOrder.getPaymentChannel();
//        List<PayOrder> transactionPaymentOrderList = new ArrayList<>(10);


//        TransactionOrder transactionRefundOrder = new TransactionOrder();
//        BeanUtils.copyProperties(transactionOrder, transactionRefundOrder);
//        transactionRefundOrder.setTransactionType(type());
//        transactionRefundOrder.setTransactionState(TransactionState.TRANSACTION_PENDING);
//        transactionRefundOrder.setPaymentState(PaymentState.PAYMENT_PENDING);

//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                transactionOrderService.save(transactionRefundOrder);
//                paymentOrderService.saveAll(transactionPaymentOrderList);
//            }
//        });
//
//        List<CompletableFuture<PayOrder>> futureList = transactionPaymentOrderList.stream().map(
//                        (PayOrder order) -> CompletableFuture.supplyAsync(() -> {
//                            PaymentResult paymentResult = refundPaymentService.call(order);
//                            order.setState(paymentResult.getState());
//                            order.setSign(paymentResult.getSign());
//                            return order;
//                        }))
//                .collect(Collectors.toList());
//        transactionResult = paymentOrderService.executePayment(transactionOrder, futureList);

        return transactionResult;
    }
}
