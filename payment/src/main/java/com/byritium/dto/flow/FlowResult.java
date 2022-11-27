package com.byritium.dto.flow;

import com.byritium.dto.PaymentDetail;
import lombok.Data;

@Data
public class FlowResult {
    private boolean goon;
    private PaymentDetail data;
}
