package com.byritium.entity.transaction;

import com.byritium.constance.*;
import com.byritium.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionTradeOrder extends CommonEntity {
    private Long id;
    private Long clientId;
    private Long bizOrderId;
    private Long uid;
    private Long payerId;
    private Long payeeId;
    private String subject;
    private BigDecimal orderAmount;
    private TransactionState transactionState = TransactionState.TRANSACTION_PENDING;
    private RefundState refundState = RefundState.TRANSACTION_NONE;
    private Long paymentPatternId;
    private PaymentState paymentState = PaymentState.PAYMENT_WAITING;

}
