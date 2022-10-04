package com.byritium.dto.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WechatPaySceneInfo {
    @JsonProperty("payer_client_ip")
    private String payerClientIp;
    @JsonProperty("h5_info")
    private WechatH5Info h5Info;

}
