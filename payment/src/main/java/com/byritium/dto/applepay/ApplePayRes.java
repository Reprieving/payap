package com.byritium.dto.applepay;

import lombok.Data;

@Data
public class ApplePayRes {
    private Receipt receipt;
    private String environment;
    private Integer status;
}
