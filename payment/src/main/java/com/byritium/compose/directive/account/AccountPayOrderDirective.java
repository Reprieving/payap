package com.byritium.compose.directive.account;

import com.byritium.compose.directive.Directive;
import com.byritium.dto.flow.FlowResult;
import com.byritium.feign.AccountPayFeign;
import com.byritium.feign.AccountRecordFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountPayOrderDirective implements Directive {

    @Autowired
    private AccountPayFeign accountPayFeign;

    @Override
    public FlowResult execute(Long paymentOrderId) {
        return null;
    }
}
