package com.byritium.compose.flow;

import com.byritium.compose.directive.Directive;

import java.util.List;

public abstract class PaymentFlowInit {
    protected String cacheKeyPrefix;
    protected List<Directive> directiveList;

    protected abstract void init();
}
