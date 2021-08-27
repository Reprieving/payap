package com.byritium.dto.wechat;

import lombok.Data;

@Data
public class WechatPayCallBackResult {
    private String id;
    private String create_time;
    private String resource_type;
    private String event_type;
    private WechatPayCallBackResource resource;
    private String summary;

}
