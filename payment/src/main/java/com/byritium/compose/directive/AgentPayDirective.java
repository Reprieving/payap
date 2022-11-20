package com.byritium.compose.directive;

import com.byritium.compose.flow.PaymentFlow;
import com.byritium.dto.PaymentExtraParam;
import com.byritium.dto.PaymentResult;
import com.byritium.service.callback.entity.PayOrder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AgentPayDirective {
    public PaymentResult execute(Long payOrderId, Long uid, BigDecimal orderAmount, String title, PaymentExtraParam param) {

        return null;
    }

    public void query(Long payOrderId) {

    }

}
