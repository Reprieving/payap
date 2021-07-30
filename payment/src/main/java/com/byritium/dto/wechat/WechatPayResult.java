package com.byritium.dto.wechat;

import lombok.Data;

@Data
public class WechatPayResult {
    private String code;
    private String message;
    private String prepay_id;
}
