package com.byritium.dto;

import lombok.Data;

@Data
public class TransactionInfo {
    private Long clientId;
    private Long bizOrderId;
    private Long paymentSettingId;
}
