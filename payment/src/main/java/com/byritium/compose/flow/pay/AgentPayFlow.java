package com.byritium.compose.flow.pay;

import com.byritium.componet.RedisClient;
import com.byritium.componet.SpringContextComp;
import com.byritium.compose.directive.AccountRecordEntryDirective;
import com.byritium.compose.directive.AgentPayOrderDirective;
import com.byritium.compose.directive.AgentPayQueryDirective;
import com.byritium.compose.directive.Directive;
import com.byritium.compose.flow.PaymentFlow;
import com.byritium.compose.flow.PaymentFlowInit;
import com.byritium.constance.payment.PaymentFlowType;
import com.byritium.dto.PaymentDetail;
import com.byritium.dto.flow.FlowResult;
import com.byritium.service.callback.entity.PayOrder;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.concurrent.Flow;

@Component
public class AgentPayFlow extends PaymentFlowInit implements PaymentFlow<PayOrder> {
    @Autowired
    private RedisClient<String, PayOrder> redisClient;

    @Override
    @PostConstruct
    protected void init() {
        cacheKeyPrefix = "PAYMENT_PAYORDER_";
        directiveList = Lists.newArrayList(
                SpringContextComp.getBean(AgentPayOrderDirective.class),
                SpringContextComp.getBean(AgentPayQueryDirective.class),
                SpringContextComp.getBean(AccountRecordEntryDirective.class)
        );
    }

    @Override
    public PaymentFlowType type() {
        return PaymentFlowType.PAY;
    }

    @Override
    public PaymentDetail start(PayOrder payOrder) {
        Long paymentOrderId = payOrder.getId();
        String key = cacheKeyPrefix + paymentOrderId;
        redisClient.set(key, payOrder, cacheExistTime());
        for(Directive directive:directiveList){
            FlowResult flowResult = directive.execute(paymentOrderId);
            if(!flowResult.isGoon()){
                return flowResult.getData();
            }
        }
        return null;
    }

    @Override
    public PaymentDetail goon(PayOrder payOrder) {

        return null;
    }


}
