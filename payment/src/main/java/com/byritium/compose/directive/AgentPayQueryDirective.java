package com.byritium.compose.directive;

import com.byritium.feign.AgentPayFeign;
import org.springframework.beans.factory.annotation.Autowired;

public class AgentPayQueryDirective implements Directive{
    @Autowired
    private AgentPayFeign agentPayFeign;

    @Override
    public void execute(Long paymentOrderId) {
        agentPayFeign.query(paymentOrderId);
    }
}
