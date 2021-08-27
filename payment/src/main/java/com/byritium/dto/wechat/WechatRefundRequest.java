package com.byritium.dto.wechat;

import lombok.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatRefundRequest {
    private String out_trade_no;//支付单号
    private String out_refund_no;//退款单号
    private String notify_url;//回调
    private WechatRefundAmount amount;

}
