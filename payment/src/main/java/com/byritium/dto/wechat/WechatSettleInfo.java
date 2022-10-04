package com.byritium.dto.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WechatSettleInfo {
    @JsonProperty("profit_sharing")
    private boolean profitSharing;
}
