package com.byritium.compose.directive;

import com.byritium.feign.AgentPayFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentPayOrderDirective implements Directive{
    @Autowired
    private AgentPayFeign agentPayFeign;

    @Override
    public void execute(Long paymentOrderId) {
        agentPayFeign.order(null);
    }
}
