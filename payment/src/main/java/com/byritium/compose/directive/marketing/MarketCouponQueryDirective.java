package com.byritium.compose.directive.marketing;

import com.byritium.compose.directive.Directive;
import com.byritium.dto.flow.FlowResult;
import org.springframework.stereotype.Component;

@Component
public class MarketCouponQueryDirective implements Directive {

    @Override
    public FlowResult execute(Long paymentOrderId) {
        return null;
    }
}
