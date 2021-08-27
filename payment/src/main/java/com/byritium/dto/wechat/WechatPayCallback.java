package com.byritium.dto.wechat;

import lombok.Data;

@Data
public class WechatPayCallback {
    private String code;
    private String message;
}
