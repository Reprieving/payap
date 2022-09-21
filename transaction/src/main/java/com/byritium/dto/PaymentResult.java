package com.byritium.dto;

import com.byritium.constance.PaymentPattern;
import com.byritium.constance.PaymentState;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.List;

@Data
public class PaymentResult {
    private String transactionOrderId;
    private PaymentPattern paymentPattern;
    private List<PaymentResultItem> details;
    private PaymentState state;

    public PaymentResultItem get(PaymentPattern paymentPattern) {
        Assert.notNull(paymentPattern,"paymentPattern require");
        return details.stream().filter(paymentResultItem -> paymentResultItem.getPaymentPattern() == paymentPattern)
                .findFirst().orElse(null);

    }

    public PaymentResultItem get() {
        Assert.notNull(paymentPattern,"paymentPattern require");
        return details.stream().filter(paymentResultItem -> paymentResultItem.getPaymentPattern() == paymentPattern)
                .findFirst().orElse(null);

    }
}
