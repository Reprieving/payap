package com.byritium.service.common;

import com.byritium.constance.PaymentState;
import com.byritium.dto.PaymentResult;
import org.springframework.stereotype.Service;

@Service
public class ValidateService {

    public boolean anyPaymentFail(PaymentResult... paymentResults) {
        for (PaymentResult paymentResult : paymentResults) {
            if (null != paymentResult && PaymentState.PAYMENT_FAIL == paymentResult.getState()) {
                return true;
            }
        }
        return false;
    }


    public boolean allPaymentSuccess(PaymentResult... paymentResults) {
        for (PaymentResult paymentResult : paymentResults) {
            if (null != paymentResult && PaymentState.PAYMENT_SUCCESS == paymentResult.getState()) {
                return true;
            }
        }
        return false;
    }
}
