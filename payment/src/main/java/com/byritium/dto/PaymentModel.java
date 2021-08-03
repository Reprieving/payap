package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import lombok.Data;

@Data
public class PaymentModel {
    private PaymentProduct productProduct;
    private PaymentChannel paymentChannel;
}
