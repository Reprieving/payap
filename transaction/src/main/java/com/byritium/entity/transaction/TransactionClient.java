package com.byritium.entity.transaction;

import com.byritium.constance.ObjectState;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionClient {
    private String id;
    private String clientName;
    private String accessKey;
    private ObjectState objectState;
    private Timestamp createTime;

}
