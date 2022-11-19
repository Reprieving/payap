package com.byritium.compose.flow;

public interface PaymentFlow<T> {
    void start(T t);

    void goon(Long paymentOrderId);
}
