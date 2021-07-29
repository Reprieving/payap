package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionProductType;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class PaymentOrder {
    private String id;
    private String clientId;
    private String businessOrderId;
    private String payerId;
    private String payeeId;
    private String subject;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private PaymentChannel paymentChannel;
    private TransactionProductType transactionProductType;
    private PaymentProduct paymentProduct;
    private PaymentState paymentState;
    private Timestamp createTime;

}
