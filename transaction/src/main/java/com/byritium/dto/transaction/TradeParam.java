package com.byritium.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TradeParam {
    private Long clientId;
    private Long businessOrderId;
    private Long userId;
    private Long payerId;
    private Long payeeId;
    private BigDecimal payAmount;
    private String title;
    private Long paymentPatternId;
    private List<Long> discountIds;
    private List<Long> couponId;
    private List<Long> virtualCurrencyIds;
}
