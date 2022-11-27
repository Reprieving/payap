package com.byritium.compose.directive;

import com.byritium.dto.flow.FlowResult;
import org.springframework.stereotype.Component;

@Component
public class AccountRecordEntryDirective implements Directive{
    @Override
    public FlowResult execute(Long paymentOrderId) {

        return null;
    }
}
