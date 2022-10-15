package com.byritium.entity.transaction;

import com.byritium.constance.*;
import com.byritium.dto.transaction.TradeParam;
import com.byritium.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TransactionTradeOrder extends CommonEntity {
    private Long id;
    private Long uid;
    private Long clientId;
    private Long bizOrderId;
    private Long payerId;
    private Long payeeId;
    private String subject;
    private BigDecimal orderAmount;
    private TransactionState transactionState = TransactionState.TRANSACTION_PENDING;
    private RefundState refundState = RefundState.TRANSACTION_NONE;
    private Long paymentSettingId;
    private PaymentState paymentState = PaymentState.PAYMENT_WAITING;


    public TransactionTradeOrder(TradeParam param){
        this.uid = param.getUid();
        this.clientId = param.getClientId();
        this.bizOrderId = param.getBizOrderId();
        this.payeeId = param.getPayeeId();
        this.payerId = param.getPayerId();
        this.subject = param.getSubject();
        this.orderAmount = param.getOrderAmount();
        this.paymentSettingId = param.getPaymentSettingId();
    }


}
