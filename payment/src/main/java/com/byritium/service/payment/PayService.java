package com.byritium.service.payment;

import com.byritium.constance.PaymentChannel;
import com.byritium.dto.PaymentResult;
import com.byritium.entity.PayOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayService {
    public PaymentResult call(PaymentChannel channel, List<PayOrder> orderList) {
        return null;
    }
}
