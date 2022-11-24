package com.byritium.dto;

import com.byritium.BusinessType;
import lombok.Data;

@Data
public class AccountRecordedParam {
    private Long paymentOrderId;
    private BusinessType businessType;
}
