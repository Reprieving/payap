package com.byritium.dto.wechat;

import lombok.Data;

@Data
public class WechatWithdrawRequest {
    private String mch_appid;
    private String mchid;
    private String nonce_str;
    private String partner_trade_no;//商户订单号
    private String openid;
    private String check_name;
    private Long amount;
    private String desc;

}
