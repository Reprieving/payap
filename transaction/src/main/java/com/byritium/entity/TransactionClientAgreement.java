package com.byritium.entity;

import com.byritium.constance.TransactionType;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionClientAgreement {
    private String id;
    private String clientId;
    private TransactionType transactionType;
    private String payerId;
    private String payeeId;
    private Timestamp createTime;
}
