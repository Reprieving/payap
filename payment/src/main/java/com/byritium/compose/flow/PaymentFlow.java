package com.byritium.compose.flow;

import com.byritium.compose.directive.Directive;
import com.byritium.constance.PaymentType;
import com.byritium.constance.payment.PaymentFlowType;
import com.byritium.service.callback.entity.PayOrder;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

public interface PaymentFlow<T> {
    PaymentFlowType type();

    default int cacheExistTime(){
        return 15;
    };

    void start(T paymentOrder);

    void goon(T paymentOrder);
}
