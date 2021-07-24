package com.byritium.service;

import com.byritium.constance.PaymentChannel;
import org.springframework.stereotype.Service;

public interface QueryService {
    PaymentChannel channel();
}
