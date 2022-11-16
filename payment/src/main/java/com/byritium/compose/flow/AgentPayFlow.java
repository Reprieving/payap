package com.byritium.compose.flow;

import com.byritium.compose.directive.AgentPayDirective;
import com.byritium.compose.directive.Directive;
import lombok.Data;

@Data
public class AgentPayFlow {
    private Directive directive;

    AgentPayFlow(){
        directive = new AgentPayDirective();
    }
}
