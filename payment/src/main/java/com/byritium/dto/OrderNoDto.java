package com.byritium.dto;

import lombok.Data;

@Data
public class OrderNoDto {
    private long bizOrderNo;
    private long trxOrderNo;
    private long prePaymentOrderNo;
    private long paymentOrderNo;
}
