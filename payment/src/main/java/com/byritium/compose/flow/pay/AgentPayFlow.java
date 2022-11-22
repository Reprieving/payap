package com.byritium.compose.flow.pay;

import com.byritium.componet.RedisClient;
import com.byritium.componet.SpringContextComp;
import com.byritium.compose.flow.PaymentFlow;
import com.byritium.constance.payment.PaymentFlowType;
import com.byritium.dto.AccountRecordedParam;
import com.byritium.dto.AgentPayParam;
import com.byritium.dto.PaymentExtraParam;
import com.byritium.feign.AccountRecordedFeign;
import com.byritium.feign.AgentPayFeign;
import com.byritium.service.callback.entity.PayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class AgentPayFlow implements PaymentFlow<PayOrder> {
    private static final String cacheKeyPrefix = "PAYMENT_PAYORDER_";

    @Autowired
    private RedisClient<String, PayOrder> redisClient;

    @Autowired
    private AgentPayFeign agentPayFeign;

    @Autowired
    private AccountRecordedFeign accountRecordedFeign;

    @Override
    public PaymentFlowType type() {
        return PaymentFlowType.PAY;
    }

    @Override
    public void start(PayOrder payOrder) {
        String key = cacheKeyPrefix + payOrder.getId();
        redisClient.set(key, payOrder, cacheExistTime());
        Long payOrderId = payOrder.getId();
        Long uid = payOrder.getUid();
        BigDecimal orderAmount = payOrder.getOrderAmount();
        String title = payOrder.getSubject();

        {
            AgentPayParam param = new AgentPayParam();
            param.setPayOrderId(payOrderId);
            param.setUid(uid);
            param.setOrderAmount(orderAmount);
            param.setTitle(title);
            agentPayFeign.order(payOrderId);
        }
        {
            agentPayFeign.query(payOrderId);
        }
        {
            AccountRecordedParam param = new AccountRecordedParam();
            accountRecordedFeign.execute(param);
        }


    }

    @Override
    public void goon(PayOrder payOrder) {

    }
}
