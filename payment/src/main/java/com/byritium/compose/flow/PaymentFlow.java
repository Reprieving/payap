package com.byritium.compose.flow;

import com.byritium.constance.payment.PaymentFlowType;
import com.byritium.dto.PaymentDetail;

public interface PaymentFlow<T> {
    PaymentFlowType type();

    default int cacheExistTime(){
        return 15;
    };

    PaymentDetail start(T paymentOrder);

    PaymentDetail goon(T paymentOrder);
}
