package com.byritium.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PayResult extends PaymentResult {
    private String sign;
}
