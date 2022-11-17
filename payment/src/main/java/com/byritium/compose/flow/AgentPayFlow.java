package com.byritium.compose.flow;

import com.byritium.compose.directive.AgentPayDirective;
import com.byritium.compose.directive.Directive;
import com.byritium.service.callback.entity.PayOrder;
import lombok.Data;
import org.reflections.vfs.Vfs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class AgentPayFlow {

    public void start(PayOrder payOrder){

    }

    public void goon(Long paymentId){

    }

}
