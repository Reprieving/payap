package com.byritium.compose.flow;

import com.byritium.constance.PaymentType;
import com.byritium.constance.payment.PaymentFlowType;
import com.byritium.service.callback.entity.PayOrder;

import java.io.Serializable;

public interface PaymentFlow<T> {
    PaymentFlowType type();

    default int cacheExistTime(){
        return 15;
    };

    void start(T paymentOrder);

    void goon(T paymentOrder);
}
