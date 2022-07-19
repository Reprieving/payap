package com.byritium.service.payment;

import com.byritium.constance.PaymentChannel;
import com.byritium.dao.PayOrderDao;
import com.byritium.dto.PaymentResult;
import com.byritium.entity.PayOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayService {

    private final PayOrderDao payOrderDao;

    public PayService(PayOrderDao payOrderDao) {
        this.payOrderDao = payOrderDao;
    }

    public PaymentResult call(PaymentChannel channel, List<PayOrder> orderList) {
        payOrderDao.saveAll(orderList);



        return null;
    }
}
