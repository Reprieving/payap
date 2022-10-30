package com.byritium.entity.transaction;

import com.byritium.constance.PaymentState;
import com.byritium.constance.RefundState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.account.AssetsType;
import com.byritium.dto.transaction.TradeParam;
import com.byritium.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TransactionRechargeOrder extends CommonEntity {
    private Long id;
    private Long uid;
    private Long clientId;
    private Long bizOrderId;
    private AssetsType assetsType;
    private String subject;
    private BigDecimal orderAmount;
    private BigDecimal rechargeAmount;
    private Long rechargeId;
    private TransactionState transactionState = TransactionState.TRANSACTION_PENDING;
    private RefundState refundState = RefundState.TRANSACTION_NONE;
    private Long paymentSettingId;
    private PaymentState paymentState = PaymentState.PAYMENT_WAITING;

}
