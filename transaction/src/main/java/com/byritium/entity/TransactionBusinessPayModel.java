package com.byritium.entity;

import com.byritium.constance.TransactionType;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TransactionBusinessPayModel {
    private String id;
    private String clientId;
    private String businessOrderId;
    private TransactionType transactionType;
    private String payerId;
    private String payeeId;
    private BigDecimal orderAmount;
    private BigDecimal payAmount;
    private Timestamp createTime;
}
