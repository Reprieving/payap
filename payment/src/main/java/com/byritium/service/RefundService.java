package com.byritium.service;

import java.math.BigDecimal;

public interface RefundService {
    void refund(String businessOrderId, BigDecimal orderAmount,BigDecimal refundAmount);
}
