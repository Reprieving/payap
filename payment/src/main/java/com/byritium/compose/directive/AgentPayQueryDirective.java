package com.byritium.compose.directive;

import com.byritium.dto.flow.FlowResult;
import com.byritium.feign.AgentPayFeign;
import org.springframework.beans.factory.annotation.Autowired;

public class AgentPayQueryDirective implements Directive{
    @Autowired
    private AgentPayFeign agentPayFeign;

    @Override
    public FlowResult<?> execute(Long paymentOrderId) {
        agentPayFeign.query(paymentOrderId);
        return null;
    }
}
