package com.byritium.dto;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.exception.BusinessException;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.List;

@Data
public class PaymentResult {
    private String transactionOrderId;
    private PaymentChannel paymentChannel;
    private List<PaymentResultItem> details;
    private PaymentState state;

    public PaymentResultItem get(PaymentChannel paymentChannel) {
        Assert.notNull(paymentChannel,"paymentChannel require");
        return details.stream().filter(paymentResultItem -> paymentResultItem.getPaymentChannel() == paymentChannel)
                .findFirst().orElse(null);

    }

    public PaymentResultItem get() {
        Assert.notNull(paymentChannel,"paymentChannel require");
        return details.stream().filter(paymentResultItem -> paymentResultItem.getPaymentChannel() == paymentChannel)
                .findFirst().orElse(null);

    }
}
