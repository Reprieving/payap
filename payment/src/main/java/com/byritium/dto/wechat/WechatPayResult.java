package com.byritium.dto.wechat;

import lombok.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatPayResult {
    private String code;
    private String message;
    private String prepay_id;
}
