package com.byritium.compose.flow;

import com.byritium.componet.SpringContextComp;
import com.byritium.compose.directive.AgentPayDirective;
import com.byritium.compose.directive.Directive;
import com.byritium.service.callback.entity.PayOrder;
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
public class AgentPayFlow {

    private static final List<Directive> directiveList = new ArrayList<>();

    @PostConstruct
    private void init() {
        directiveList.add(SpringContextComp.getBean(AgentPayDirective.class));
    }

    public void start(PayOrder payOrder) {

    }

    public void goon(Long paymentId) {

    }

}
