package com.byritium.service.payment;

import com.byritium.constance.PaymentPattern;
import com.byritium.dao.PayOrderDao;
import com.byritium.dto.IdContainer;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.ResponseBody;
import com.byritium.service.callback.entity.PayOrder;
import com.byritium.rpc.ChannelPaymentRpc;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayService {
    private final PayOrderDao payOrderDao;
    private final ChannelPaymentRpc channelPaymentRpc;

    public PayService(PayOrderDao payOrderDao, ChannelPaymentRpc channelPaymentRpc) {
        this.payOrderDao = payOrderDao;
        this.channelPaymentRpc = channelPaymentRpc;
    }

    public PaymentResult call(PaymentPattern channel, IdContainer idContainer, String subject, BigDecimal payAmount) {

        List<PayOrder> orderList = new ArrayList<>();
        payOrderDao.saveAll(orderList);
        PayOrder payOrder = orderList.stream().filter(order -> channel == order.getPaymentPattern()).findFirst().get();

        ResponseBody<PaymentResult> rsp = channelPaymentRpc.pay(payOrder);
        PaymentResult paymentResult = rsp.getData();

        return paymentResult;
    }

    public void callback(String bizOrderId, String payOrderId) {

    }
}
