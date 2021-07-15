package com.byritium.entity;

import com.byritium.constance.Os;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionClient {
    private String id;
    private String clientName;
    private String accessKey;
    private Os os;
    private Timestamp createTime;

}
