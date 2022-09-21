package com.byritium.service.payment;

import com.byritium.constance.PaymentPattern;
import com.byritium.service.callback.entity.PayOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefundService {
    public void call(PaymentPattern channel, List<PayOrder> orderList) {

    }
}
