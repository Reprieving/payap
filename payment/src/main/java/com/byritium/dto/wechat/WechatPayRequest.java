package com.byritium.dto.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatPayRequest {
    private String appid;//公众号id
    private String mchid;//商户号
    private String description;//描述
    @JsonProperty("out_trade_no")
    private String outTradeNo;//商户订单号
    @JsonProperty("notify_url")
    private String notifyUrl;
    private WechatPayAmount amount;
    @JsonProperty("scene_info")
    private WechatPaySceneInfo sceneInfo;
    @JsonProperty("settle_info")
    private WechatSettleInfo settleInfo;
}
