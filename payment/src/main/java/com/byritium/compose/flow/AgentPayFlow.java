package com.byritium.compose.flow;

import com.byritium.componet.SpringContextComp;
import com.byritium.compose.directive.AgentPayDirective;
import com.byritium.compose.directive.AgentPayQueryDirective;
import com.byritium.compose.directive.Directive;
import com.byritium.compose.directive.RecordDirective;
import com.byritium.service.callback.entity.PayOrder;
import com.byritium.service.callback.entity.PaymentOrder;
import lombok.Data;
import org.apache.catalina.core.ApplicationContext;
import org.reflections.vfs.Vfs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class AgentPayFlow implements PaymentFlow {

    private static final List<Directive> directiveList = new ArrayList<>();

    @PostConstruct
    private void init() {
        directiveList.add(SpringContextComp.getBean(AgentPayDirective.class));
        directiveList.add(SpringContextComp.getBean(AgentPayQueryDirective.class));
        directiveList.add(SpringContextComp.getBean(RecordDirective.class));
    }


    @Override
    public void start() {

    }

    @Override
    public void goon() {

    }
}
