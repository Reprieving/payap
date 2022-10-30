package com.byritium.dto.payment;

import com.byritium.constance.payment.PrepayParamType;
import lombok.Data;

@Data
public class PrepayParam {
    private PrepayParamType paramType;
    private String prepayData;
}
