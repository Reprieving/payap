package com.byritium.dto.wechat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatPayCallBackResult {
    private String id;
    private String create_time;
    private String resource_type;
    private String event_type;
    private WechatPayCallBackResource resource;
    private String summary;

}
