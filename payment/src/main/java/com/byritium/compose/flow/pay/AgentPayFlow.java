package com.byritium.compose.flow.pay;

import com.byritium.componet.RedisClient;
import com.byritium.componet.SpringContextComp;
import com.byritium.compose.directive.AccountRecordEntryDirective;
import com.byritium.compose.directive.AgentPayOrderDirective;
import com.byritium.compose.directive.AgentPayQueryDirective;
import com.byritium.compose.flow.PaymentFlow;
import com.byritium.compose.flow.PaymentFlowInit;
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
public class AgentPayFlow extends PaymentFlowInit implements PaymentFlow<PayOrder> {
    @Autowired
    private RedisClient<String, PayOrder> redisClient;

    @Autowired
    private AgentPayFeign agentPayFeign;

    @Autowired
    private AccountRecordedFeign accountRecordedFeign;

    @Override
    protected void init() {
        cacheKeyPrefix = "PAYMENT_PAYORDER_";
        directiveList = new ArrayList<>();
        directiveList.add(SpringContextComp.getBean(AgentPayOrderDirective.class));
        directiveList.add(SpringContextComp.getBean(AgentPayQueryDirective.class));
        directiveList.add(SpringContextComp.getBean(AccountRecordEntryDirective.class));
    }

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


    }

    @Override
    public void goon(PayOrder payOrder) {

    }


}
