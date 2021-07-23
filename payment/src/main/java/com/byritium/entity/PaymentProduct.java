package com.byritium.entity;

import com.byritium.constance.PaymentProductType;
import com.byritium.constance.TransactionProductType;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentProduct {
    private String id;
    private TransactionProductType transactionProductType;
    private PaymentProductType paymentProductType;
    private Timestamp createTime;
}
