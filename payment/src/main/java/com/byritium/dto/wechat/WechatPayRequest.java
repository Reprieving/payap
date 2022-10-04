package com.byritium.dto.wechat;

import lombok.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatPayRequest {
    private String appid;//公众号id
    private String mchid;//商户号
    private String description;//描述
    private String out_trade_no;//商户订单号
    private String notify_url;
    private WechatPayAmount amount;
    private WechatPaySceneInfo scene_info;
    private WechatSettleInfo settle_info;
}
