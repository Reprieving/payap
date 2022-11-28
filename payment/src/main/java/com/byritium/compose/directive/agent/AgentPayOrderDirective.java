package com.byritium.compose.directive.agent;

import com.byritium.compose.directive.Directive;
import com.byritium.dto.flow.FlowResult;
import com.byritium.feign.AgentPayFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentPayOrderDirective implements Directive {
    @Autowired
    private AgentPayFeign agentPayFeign;

    @Override
    public FlowResult execute(Long paymentOrderId) {
        agentPayFeign.order(paymentOrderId);
        return null;
    }
}
