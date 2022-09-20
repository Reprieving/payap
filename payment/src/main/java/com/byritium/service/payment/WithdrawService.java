package com.byritium.service.payment;

import com.byritium.constance.PaymentChannel;
import com.byritium.service.callback.entity.PayOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawService {
    public void call(PaymentChannel channel, List<PayOrder> orderList) {

    }
}
