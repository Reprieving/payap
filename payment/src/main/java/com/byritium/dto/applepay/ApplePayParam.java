package com.byritium.dto.applepay;

import lombok.Data;

@Data
public class ApplePayParam {
    private Integer environmentType;
    private String transactionId;
}
