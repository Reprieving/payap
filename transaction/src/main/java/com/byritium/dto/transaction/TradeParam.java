package com.byritium.dto.transaction;

import com.byritium.dto.VirtualCurrency;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TradeParam {
    private Long clientId;
    private Long bizOrderId;
    private Long uid;
    private Long payerId;
    private Long payeeId;
    private BigDecimal orderAmount;
    private String subject;
    private Long paymentSettingId;
    private Long discountId;
    private Long couponId;
    private VirtualCurrency virtualCurrency;
}
