package com.byritium.entity;

import com.byritium.constance.TransactionType;
import lombok.Data;

@Data
public class TransactionProduct {
    private String id;
    private TransactionType transactionType;

}
