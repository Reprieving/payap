package com.byritium.compose.directive;

import com.byritium.dto.flow.FlowResult;

public interface Directive {
    FlowResult execute(Long paymentOrderId);
}
