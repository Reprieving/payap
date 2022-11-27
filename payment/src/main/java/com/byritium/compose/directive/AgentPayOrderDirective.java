package com.byritium.compose.directive;

import com.byritium.dto.flow.FlowResult;
import com.byritium.feign.AgentPayFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentPayOrderDirective implements Directive{
    @Autowired
    private AgentPayFeign agentPayFeign;

    @Override
    public FlowResult execute(Long paymentOrderId) {
        agentPayFeign.order(null);
        return null;
    }
}
