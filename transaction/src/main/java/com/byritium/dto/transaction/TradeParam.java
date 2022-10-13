package com.byritium.dto.transaction;

import com.byritium.dto.VirtualCurrency;
import lombok.Data;

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
    private List<Long> discountIds;
    private List<Long> couponIds;
    private List<VirtualCurrency> virtualCurrencies;
}
