package com.byritium.compose.flow.pay;

import com.byritium.componet.RedisClient;
import com.byritium.componet.SpringContextComp;
import com.byritium.compose.directive.*;
import com.byritium.compose.flow.PaymentFlow;
import com.byritium.constance.payment.PaymentFlowType;
import com.byritium.dto.PaymentExtraParam;
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
    private AgentPayDirective agentPayDirective;

    @Autowired
    private RecordedDirective recordedDirective;

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

        recordedDirective.execute();
    }

    @Override
    public void goon(PayOrder payOrder) {

    }
}
