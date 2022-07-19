package com.byritium.service.payment;

import com.byritium.constance.PaymentChannel;
import com.byritium.dao.PayOrderDao;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.PayOrder;
import com.byritium.rpc.ChannelPaymentRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayService {


    private final PayOrderDao payOrderDao;
    private final ChannelPaymentRpc channelPaymentRpc;

    public PayService(PayOrderDao payOrderDao, ChannelPaymentRpc channelPaymentRpc) {
        this.payOrderDao = payOrderDao;
        this.channelPaymentRpc = channelPaymentRpc;
    }

    public PaymentResult call(PaymentChannel channel, List<PayOrder> orderList) {
        payOrderDao.saveAll(orderList);
        PayOrder payOrder = orderList.stream().filter(payOrder1 -> channel == payOrder1.getPaymentChannel()).findFirst().get();

        ResponseBody<PaymentResult> rsp = channelPaymentRpc.pay(payOrder);
        PaymentResult paymentResult = rsp.getData();

        return paymentResult;
    }
}
