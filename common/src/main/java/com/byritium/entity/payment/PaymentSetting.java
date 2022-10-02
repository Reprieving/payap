package com.byritium.entity.payment;

import com.byritium.constance.*;
import lombok.Data;

@Data
public class PaymentSetting {
    private PaymentScene scene;
    private PaymentProduct product;
    private PaymentChannel channel;
    private PaymentApplication application;
}
