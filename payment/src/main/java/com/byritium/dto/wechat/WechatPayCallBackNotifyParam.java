package com.byritium.dto.wechat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatPayCallBackNotifyParam {
    private String id;
    private String appid;//appid
    private String mchid;//商户id
    private String out_trade_no;//商户订单号
    private String trade_state;//交易状态
    private String trade_state_desc;//交易状态描述
}
