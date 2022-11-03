package com.byritium.entity.transaction;

import com.byritium.constance.PaymentState;
import com.byritium.constance.RefundState;
import com.byritium.constance.TransactionState;
import com.byritium.constance.account.AssetsType;
import com.byritium.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class WithdrawOrder extends CommonEntity {
    private Long id;
    private Long uid;
    private Long clientId;
    private Long bizOrderId;
    private String subject;
    private BigDecimal withdrawAmount;
    private TransactionState transactionState = TransactionState.TRANSACTION_PENDING;
    private Long paymentSettingId;
    private PaymentState paymentState = PaymentState.PAYMENT_WAITING;

}
